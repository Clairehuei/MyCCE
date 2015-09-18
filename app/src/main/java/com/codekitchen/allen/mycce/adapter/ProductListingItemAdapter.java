package com.codekitchen.allen.mycce.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codekitchen.allen.mycce.R;
import com.codekitchen.allen.mycce.api.model.ProductListingItem;
import com.codekitchen.allen.mycce.events.ProductListingItemClickedEvent;
import com.squareup.otto.Bus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 6193 on 2015/9/15.
 */
public class ProductListingItemAdapter extends RecyclerView.Adapter<ProductListingItemAdapter.ViewHolder> {
    private final Context context;
    private final Bus bus;
    private List<ProductListingItem> items;

    //[呼叫api返回查詢結果list顯示流程->02]
    public ProductListingItemAdapter(Context context, Bus bus, List<ProductListingItem> items) {
        this.context = context;
        this.bus = bus;
        this.items = items;
        Log.d("=[Prod Adapter]=","===new ProductListingItemAdapter()===");
    }


    //[呼叫api返回查詢結果list顯示流程->07]
    public void updateItems(List<ProductListingItem> items) {
        this.items = items;
        Log.d("=[Prod Adapter]=","===updateItems===");
    }


    //[呼叫api返回查詢結果list顯示流程->09]
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//创建ViewHolder
        Log.d("=[Prod Adapter]=","===onCreateViewHolder===");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product_listing_item, parent, false);

        return new ViewHolder(view);
    }


    //[呼叫api返回查詢結果list顯示流程->11]
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//將數據绑定至 ViewHolder
        Log.d("=[Prod Adapter]=","===onBindViewHolder===");
        ProductListingItem item = items.get(position);
        holder.item = item;
        holder.title.setText(item.getName());
//        holder.priceUnit.setText(item.getPriceUnit(context));
//        holder.category.setText(item.getCategory());
    }


    @Override
    public int getItemCount() {//獲取總筆數
        Log.d("=[Prod Adapter]=","===getItemCount===");
        return items.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ProductListingItem item;

        @InjectView(android.R.id.text1)
        TextView title;
//
//        @InjectView(android.R.id.text2)
//        TextView priceUnit;
//
//        @InjectView(R.id.category)
//        TextView category;


        //[呼叫api返回查詢結果list顯示流程->10]
        public ViewHolder(View itemView) {
            super(itemView);
            Log.d("ViewHolder", "===ViewHolder(1)===");
            ButterKnife.inject(this, itemView);
            Log.d("ViewHolder", "===ViewHolder(2)===");
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Log.d("ViewHolder", "===onClick===");
            bus.post(new ProductListingItemClickedEvent(item.getId()));
        }
    }


}
