package com.example.tfg2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tfg2.Models.Producto;
import com.example.tfg2.fragments.CartFragment;
import com.example.tfg2.fragments.HomeFragment;
import com.example.tfg2.fragments.MyProductsFragment;
import com.example.tfg2.fragments.ProfileFragment;
import com.example.tfg2.utilidades.ImagenesFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class Add_product_to_my_productsActivity extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser user;

    ArrayList<String> categoriasList =(ArrayList<String>) HomeFragment.categoriesList;

    public static final int NUEVA_IMAGEN = 1;
    Uri imagen_seleccionada = null;

    EditText edt_product_name,edt_product_description,edt_product_price;
    Spinner sp_category;
    Button bt_add_product;
    ImageView img_product;

    Producto p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_to_my_products);





        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        img_product = findViewById(R.id.img_product);
        edt_product_name = findViewById(R.id.edt_product_name);
        edt_product_description = findViewById(R.id.edt_product_description);
        edt_product_price = findViewById(R.id.edt_product_price);
        sp_category = findViewById(R.id.sp_category);
        bt_add_product = findViewById(R.id.bt_add_product);

        categoriasList.remove("Todas");

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, categoriasList);
        sp_category.setAdapter(adapter);

        bt_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_to_my_prodcuts();

                Intent intent = new Intent(Add_product_to_my_productsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    //--------CODIGO PARA CAMBIAR LA IMAGEN----------------
    public void cambiar_imagen(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/");


        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, NUEVA_IMAGEN);

        img_product.setTag("otro");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NUEVA_IMAGEN && resultCode == Activity.RESULT_OK) {
            imagen_seleccionada = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagen_seleccionada);
                img_product.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void add_to_my_prodcuts(){
        String product_name = edt_product_name.getText().toString();
        String product_description = edt_product_description.getText().toString();
        String product_price = edt_product_price.getText().toString();
        String product_id= database.getReference().push().getKey();
        String product_category = sp_category.getSelectedItem().toString();


        if(!img_product.getTag().equals("foto"))
        {
            new ImagenesFirebase().subirFoto(new ImagenesFirebase.FotoStatus() {
                @Override
                public void FotoIsDownload(byte[] bytes) {
                }
                @Override
                public void FotoIsDelete() {
                }
                @Override
                public void FotoIsUpload() {
                    Toast.makeText(Add_product_to_my_productsActivity.this,"foto subida correcta",Toast.LENGTH_LONG).show();
                }
            },product_name, product_price, img_product);


            p = new Producto(HomeActivity.nombre,product_name, product_price,product_category,product_description,product_name+"/"+String.valueOf(product_price)+".png",product_id);
        }
        else{
            Toast.makeText(Add_product_to_my_productsActivity.this,"ERROR Seleccione una imagen",Toast.LENGTH_LONG).show();
        }


        database.getReference().child("myproducts").child(user.getUid()).child(product_id).setValue(p);
        database.getReference().child("products_second_hand").child(product_id).setValue(p);
    }

    public void add_to_second_hand(){





    }
}