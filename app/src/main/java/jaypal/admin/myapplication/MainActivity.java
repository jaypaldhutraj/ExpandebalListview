package jaypal.admin.myapplication;

import android.content.pm.FeatureGroupInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {
    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();

        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);

        listAdapter = new CustomAdapter(MainActivity.this, deptList);

        simpleExpandableListView.setAdapter(listAdapter);


        expandAll();
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                GroupInfo headerInfo = deptList.get(groupPosition);

                ChildInfo detailInfo =  headerInfo.getProductList().get(childPosition);

                Toast.makeText(getBaseContext(), " Clicked on :: " + headerInfo.getName()
                        + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();
                return false;
            }
        });

        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                GroupInfo headerInfo = deptList.get(groupPosition);

                Toast.makeText(getBaseContext(), " Header is :: " + headerInfo.getName(),
                        Toast.LENGTH_LONG).show();

                return false;
            }
        });


    }


    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.expandGroup(i);
        }
    }


    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.collapseGroup(i);
        }
    }


    private void loadData(){

        addProduct("Android","Topic Covered");
        addProduct("Android","Topic not Covered");
        addProduct("Android","ExpandebableList view");

        addProduct("Java","PolyMorphism");
        addProduct("Java","Collections");

    }




    private int addProduct(String department, String product){

        int groupPosition = 0;


        GroupInfo headerInfo = subjects.get(department);

        if(headerInfo == null){
            headerInfo = new GroupInfo();
            headerInfo.setName(department);
            subjects.put(department, headerInfo);
            deptList.add(headerInfo);
        }


        ArrayList<ChildInfo> productList = headerInfo.getProductList();

        int listSize = productList.size();

        listSize++;


        ChildInfo detailInfo = new ChildInfo();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);


        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }




}
