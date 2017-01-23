package com.example.prashant.demoapplication;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prashant.demoapplication.model.DataModel;
import com.example.prashant.demoapplication.restApi.ApiInterface;
import com.example.prashant.demoapplication.restApi.RestBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    private RecyclerView mListView;
    private SwipeRefreshLayout mRefreshController;
    private TextView mNoDataView;

    private ListAdapter mAdapter;
    private List<DataModel> mDataModel;

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoDataView = (TextView) findViewById(R.id.noData);

        mListView = (RecyclerView) findViewById(R.id.listView);
        mAdapter = new ListAdapter();
        mListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mListView.setAdapter(mAdapter);

        mRefreshController = (SwipeRefreshLayout) findViewById(R.id.refreshController);

        mRefreshController.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                performRefreshTask();
            }
        });

        mDataModel = new ArrayList<>();

    }

    private void performRefreshTask() {

        mRefreshController.setRefreshing(true);

        ApiInterface apiInterface = RestBuilder.getInstance().create(ApiInterface.class);

        mDataModel.clear();

        apiInterface.getPostData();

        Call<List<DataModel>> data = apiInterface.getPostData();
        data.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                mDataModel.addAll(response.body());
                mRefreshController.setRefreshing(false);
                mAdapter.notifyDataSetChanged();
                mNoDataView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                t.printStackTrace();
                mRefreshController.setRefreshing(false);
                mNoDataView.setVisibility(View.VISIBLE);
                ShowError(getResources().getString(R.string.svrErr));
            }
        });

        mAdapter.notifyDataSetChanged();
    }

    private void ShowError(String err) {
        Toast.makeText(getApplicationContext(),err,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        performRefreshTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_view_row,parent,false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            DataModel model = mDataModel.get(position);

            String title = model.getTitle();
            String body = model.getBody();

            holder.title.setText(title);
            holder.body.setText(body);

        }

        @Override
        public int getItemCount() {
            return mDataModel.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView title,body;

            public ViewHolder(View itemView) {
                super(itemView);

                title = (TextView) itemView.findViewById(R.id.title);
                body = (TextView) itemView.findViewById(R.id.body);
            }
        }
    }
}
