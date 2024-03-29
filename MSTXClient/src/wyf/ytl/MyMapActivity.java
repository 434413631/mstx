package wyf.ytl;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
public class MyMapActivity extends MapActivity implements OnClickListener{	
	MapController myMapController = null;//声明myMapController的引用
	MapView myMapView = null;//声明MapView的引用
	double info_lon = 116.458679;//经度
	double info_lat = 39.917124;//纬度
	ImageButton MyImageButton = null;
	boolean inMove = true;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //全屏  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setContentView(R.layout.map);
		myMapView = (MapView) this.findViewById(R.id.myMapView);//得到myMapView的引用
		myMapController = myMapView.getController();//获得MapController
		GeoPoint gp = new GeoPoint((int)(info_lat*1E6), (int)(info_lon*1E6));
		myMapController.animateTo(gp);//设置经纬度
		myMapController.setZoom(15);//设置缩放比例
		myMapView.setBuiltInZoomControls(true);
		MyImageButton = (ImageButton)findViewById(R.id.MyImageButton);
		MyImageButton.setOnClickListener(this);
		MyMapOverlay myOverlay = new MyMapOverlay();
		List<Overlay> overlays = myMapView.getOverlays();
		overlays.clear();
		overlays.add(myOverlay); 
	}
	protected boolean isRouteDisplayed() {
		return false;
	}
	public void onClick(View v) {
		if(v == MyImageButton){//确定按钮
			boolean flag = true;
        	List<Overlay> overlays = myMapView.getOverlays(); 
        	for(Overlay ol:overlays){
        		if(ol instanceof MyBallonOverlay){
        			flag = false;
        			GeoPoint gp = ((MyBallonOverlay) ol).getGeoPoint();
        			Intent intent = new Intent();
        			Bundle bundle = new Bundle();
        			bundle.putDouble("lat", gp.getLatitudeE6()/1E6);
        			bundle.putDouble("lon", gp.getLongitudeE6()/1E6);        			
        			intent.putExtras(bundle);
        			MyMapActivity.this.setResult(RESULT_OK, intent);
        			MyMapActivity.this.finish();
        		}
        	}
        	if(flag){
        		Toast.makeText(this, "请点击地图获取店铺位置", Toast.LENGTH_LONG).show();
        	}
		}
	}
}