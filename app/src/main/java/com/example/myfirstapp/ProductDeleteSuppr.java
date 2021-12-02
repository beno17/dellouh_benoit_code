package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myfirstapp.Dao.ProductDao;
import com.example.myfirstapp.entities.Product;
import com.example.myfirstapp.vebServices.ProductWebService;

public class ProductDeleteSuppr extends AppCompatActivity {
    private Button del ;
    private TextView msg ;
    private ProductDao productDao ;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_delete_suppr);
        productDao = new ProductDao(this);

        Product product =   (Product) getIntent().getSerializableExtra("PROD_D");

        msg = findViewById(R.id.del_confirm_text);
        del = findViewById(R.id.confirm_del);

        msg.setText("Vous etes sur le  point de supprimer le produit "+product.name+".\n Cliquez sur le bouton pour continuer la suppression");

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(
                        ()->{
                            ProductWebService productWebService = new ProductWebService();
                            Product save = productWebService.deleteProduct(product);
                            System.out.println(save);
                            System.out.println("save :: " + save);
                            runOnUiThread(()->{
                                if (save != null){
                                    productDao.delete(product.id);
                                }
                            });
                        }
                ).start();

                Intent intent = new Intent(ProductDeleteSuppr.this, ProductList.class);
                startActivity(intent);
                finish();
            }
        });

    }
}