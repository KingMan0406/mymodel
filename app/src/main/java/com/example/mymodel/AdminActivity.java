package com.example.mymodel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mymodel.databinding.ActivityAdminBinding;
import com.example.mymodel.models.ModelM;
import com.example.mymodel.remote_data.Api;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Api api;
    private ListView userListView;
    private Button addButton ,btnBack;
    private Button selectImageButton;
    private EditText productName, productPrice, productDescription, productCount;
    private ImageView productImageView;
    private ArrayAdapter<String> adapter;
    private List<ModelM> products;
    private Bitmap selectedBitmap;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userListView = findViewById(R.id.user_list_view);
        addButton = findViewById(R.id.add_button);
        selectImageButton = findViewById(R.id.select_image_button);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.product_description);
        productCount = findViewById(R.id.product_count);
        productImageView = findViewById(R.id.product_image_view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        fetchProducts();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                InputStream imageStream = getContentResolver().openInputStream(selectedImageUri);
                selectedBitmap = BitmapFactory.decodeStream(imageStream);
                productImageView.setImageBitmap(selectedBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void fetchProducts() {
        Call<List<ModelM>> call = api.getStoreProducts();
        call.enqueue(new Callback<List<ModelM>>() {
            @Override
            public void onResponse(Call<List<ModelM>> call, Response<List<ModelM>> response) {
                if (response.isSuccessful()) {
                    products = response.body();
                    updateProductList();
                }
            }

            @Override
            public void onFailure(Call<List<ModelM>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void updateProductList() {
        List<String> productNames = new ArrayList<>();
        for (ModelM product : products) {
            productNames.add(product.getModelTitle());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productNames);
        userListView.setAdapter(adapter);
    }

    private void addProduct() {
        String name = productName.getText().toString();
        double price = Double.parseDouble(productPrice.getText().toString());
        String description = productDescription.getText().toString();
        int count = Integer.parseInt(productCount.getText().toString());

        if (selectedImageUri != null) {
            try {
                InputStream imageStream = getContentResolver().openInputStream(selectedImageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();
                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                RequestBody namePart = RequestBody.create(MultipartBody.FORM, name);
                RequestBody pricePart = RequestBody.create(MultipartBody.FORM, String.valueOf(price));
                RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, description);
                RequestBody countPart = RequestBody.create(MultipartBody.FORM, String.valueOf(count));
                RequestBody imagePart = RequestBody.create(MediaType.parse("image/*"), imageBytes);
                MultipartBody.Part image = MultipartBody.Part.createFormData("image", "image.jpg", imagePart);

                Call<ModelM> call = api.addProduct(namePart, pricePart, descriptionPart, countPart, image);
                call.enqueue(new Callback<ModelM>() {
                    @Override
                    public void onResponse(Call<ModelM> call, Response<ModelM> response) {
                        if (response.isSuccessful()) {
                            // Успешное добавление продукта
                            Toast.makeText(AdminActivity.this, "Product uploaded successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // Ошибка при добавлении продукта
                            try {
                                String errorBody = response.errorBody().string();
                                Log.e("UploadError", "Failed to upload product: " + errorBody);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(AdminActivity.this, "Failed to upload product", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelM> call, Throwable t) {
                        // Обработка ошибки
                        Log.e("UploadError", "An error occurred", t);
                        Toast.makeText(AdminActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }
}
