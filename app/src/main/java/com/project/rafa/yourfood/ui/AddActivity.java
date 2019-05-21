package com.project.rafa.yourfood.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.data.Food;

public class AddActivity extends AppCompatActivity {

    EditText edName, edPreparation, edIngredients, edPrice;
    ImageView img;
    FloatingActionButton btnAdd;

    private StorageReference mStorage;

    String urlImg;
    Uri selectedImage;

    private ProgressBar mProgressBar;

    private static final int REQUEST_CAMERA = 0;
    private static final int SELECT_FILE = 1;
    public final int IMAGE_PICK_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edName = findViewById(R.id.ed_name_add);
        edPreparation = findViewById(R.id.ed_preparation_add);
        edIngredients = findViewById(R.id.ed_ingredients_add);
        edPrice = findViewById(R.id.ed_price_add);
        img = findViewById(R.id.img_add_food);
        btnAdd = findViewById(R.id.btn_add);
        mProgressBar = findViewById(R.id.progress_bar);

        mStorage = FirebaseStorage.getInstance().getReference();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImage();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFood();
            }
        });
    }

    void showImage(){
        final CharSequence[] items = {"Cámara", "Galería"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Seleccionar foto");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Cámara")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                    dialog.dismiss();
                } else if (items[item].equals("Galería")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,SELECT_FILE);
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    void addFood(){
        final String name = edName.getText().toString().trim();
        final String prep = edPreparation.getText().toString().trim();
        final String ingredients = edIngredients.getText().toString().trim();
        final String price = edPrice.getText().toString().trim();

        final String idU = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        if(selectedImage != null){
            mProgressBar.setVisibility(View.VISIBLE);
            final StorageReference reference = mStorage.child("imgFood").child(idU).child(selectedImage.getLastPathSegment());
            reference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            urlImg = uri.toString();
                            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("food").document();
                            String foodId = documentReference.getId();
                            Food food = new Food(name, ingredients, prep, "tipo 1", foodId, urlImg, price, idU);
                            documentReference.set(food);
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(AddActivity.this, "Plato Agregado!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            });
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Toast.makeText(this, "code: "+ requestCode, Toast.LENGTH_SHORT).show();
        switch(requestCode) {
            case 0:
                Toast.makeText(this, ""+ resultCode + " --- " +RESULT_OK, Toast.LENGTH_SHORT).show();
                if(requestCode == REQUEST_CAMERA && resultCode == RESULT_OK){
                    Toast.makeText(this, "positron", Toast.LENGTH_SHORT).show();
                    Bundle extras = imageReturnedIntent.getExtras();
                    Bitmap photo = (Bitmap) extras.get("data");
                    img.setImageBitmap(photo);
//                    selectedImage = imageReturnedIntent.getData();
//                    img.setImageURI(selectedImage);
                }

                break;
            case 1:
                Toast.makeText(this, ""+ resultCode, Toast.LENGTH_SHORT).show();
                if(resultCode == RESULT_OK){
                    selectedImage = imageReturnedIntent.getData();
                    img.setImageURI(selectedImage);
                }
                break;
        }
    }
}
