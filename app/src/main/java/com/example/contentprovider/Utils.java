package com.example.contentprovider;

import java.util.ArrayList;

public class Utils {
    static ArrayList<Products>productsArrayList=new ArrayList<>();
    static ArrayList<String>categoryArrayList=new ArrayList<>();
    public static void FillCategoryArrayList(){
        categoryArrayList.add("Food");
        categoryArrayList.add("Fast Food");
        categoryArrayList.add("Drinks");
        categoryArrayList.add("Ice");
        categoryArrayList.add("Ice cream");

    }
    public static void FillProductsArrayList(){
        for (int i = 0; i < 50; i++) {
            productsArrayList.add(new Products(i+1,"Product #"+i,R.drawable.ic_baseline_icecream_24,i*5+10,categoryArrayList.get(i%5)));

        }
    }
    public static void FillData(){
        FillCategoryArrayList();
        FillProductsArrayList();
    }
    public static ArrayList<Products> GetProductsByCategory(String category){
        ArrayList<Products> p=new ArrayList<>();
        for (int i=0;i<productsArrayList.size();i++){
            if (category.equals(productsArrayList.get(i).getCategory())){
                p.add(productsArrayList.get(i));
            }

        }
        return p;
    }




}
