package com.tata.view;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tata.R;
import com.tata.bean.NearTravel;
import com.tata.bean.ShareMessage;
import com.tata.utils.Constants;
import com.tata.utils.MainApplication;
import com.tata.utils.MyHttpClient;
import com.tata.utils.NetWorkHelper;
import com.tata.utils.jsonTools;

import android.R.integer;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.BaseAdapter;

public class CollectSharePullTask {

	private PullToRefreshListView pullToRefreshListView; // 实现下拉刷新与上拉加载的ListView
	private int pullState; // 记录判断，上拉与下拉动作
	private BaseAdapter baseAdapter; // ListView适配器，用于提醒ListView数据已经更新
	private List<ShareMessage> linkedList;
	private Context context;
	private String phoneNumber;
	private Boolean collect;
	public CollectSharePullTask(Context mContext,
			PullToRefreshListView pullToRefreshListView, int pullState,
			BaseAdapter baseAdapter, List<ShareMessage> linkedList,
			String phone) {
		this.pullToRefreshListView = pullToRefreshListView;
		this.pullState = pullState;
		this.baseAdapter = baseAdapter;
		this.linkedList = linkedList;
		context = mContext;
		phoneNumber = phone;
	}

	private RequestParams param;
	String url;

	public void getShare() {
		if (NetWorkHelper.checkNetState(context)) {
			AsyncHttpClient httpClient = new AsyncHttpClient();
			param = new RequestParams();
			url="";
			if (pullState == 1) {
//				url = "http://120.24.254.127/tata/getShareUp.php";
//				param.put("newShareID", linkedList.getFirst().getShareID());
			} else{
					Constants.collect_share++;
				param.put("num", Constants.collect_share);
				param.put("phoneNumber", phoneNumber);
				url="http://120.24.254.127/tata/data/getCollect.php";
			}
			httpClient.post(url, param, new TextHttpResponseHandler() {

				@Override
				public void onSuccess(int arg0, Header[] arg1, String result) {
					// TODO Auto-generated method stub
					if (pullState == 1) {// name="pullDownFromTop" value="0x1"
											// 下拉
//						LinkedList<ShareMessage> newData = jsonTools
//								.getShareMessages(result);
//						for (int i = newData.size() - 1; i >= 0; i--) {
//							linkedList.addFirst(newData.get(i));
//							baseAdapter.notifyDataSetChanged();
//						}
					}
					if (pullState == 2) {// name="pullUpFromBottom" value="0x2"
											// 上拉
						List<ShareMessage> newData = jsonTools
								.getMyCollectMessages(result);
						for (int i = 0; i < newData.size(); i++) {
							linkedList.add(newData.get(i));
							baseAdapter.notifyDataSetChanged();
						}
					}
					pullToRefreshListView.onRefreshComplete();
				}

				@Override
				public void onFailure(int arg0, Header[] arg1, String arg2,
						Throwable arg3) {
//					System.out.println(arg2);
					pullToRefreshListView.onRefreshComplete();
				}
			});
		}else {
			pullToRefreshListView.onRefreshComplete();
		}
	}

}
