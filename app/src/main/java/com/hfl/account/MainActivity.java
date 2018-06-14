package com.hfl.account;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Account> list;
    private AccountDatabase ad;
    private EditText nameET;
    private EditText balanceET;
    private MyAdapter adapter;
    private RecyclerView accountRV;
    private SwipeRefreshLayout srl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accountRV = (RecyclerView) findViewById(R.id.accountLV);
        nameET = (EditText) findViewById(R.id.nameET);
        balanceET = (EditText) findViewById(R.id.balanceET);
        srl=(SwipeRefreshLayout)findViewById(R.id.swip);
        srl.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_green_light);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srl.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        accountRV.setLayoutManager(layoutManager);
        ad = new AccountDatabase(this);
        /* 从数据库查询并获取所有数据 */
        list = ad.queryAll();
        adapter = new MyAdapter(this, ad, list);
        accountRV.setAdapter(adapter);
    }
    public void add(View v) {//添加响应事件
        String name = nameET.getText().toString().trim();
        String balance = balanceET.getText().toString().trim();
        Account a = new Account(name, balance.equals("") ? 0
                : Integer.parseInt(balance));
        ad.insert(a);                      // 插入数据库
        list.add(a);                        // 插入集合
        adapter.notifyDataSetChanged(); // 刷新界面
        accountRV.scrollToPosition(accountRV.getChildCount()-1);
        nameET.setText("");
        balanceET.setText("");
    }
}
