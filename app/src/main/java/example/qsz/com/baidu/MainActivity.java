package example.qsz.com.baidu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);    // 支持js
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);  // 配置WebView加载的混合模式http,https
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过js打开新窗口
        webView.getSettings().setAllowFileAccess(true);   // 访问本地文件
        webView.getSettings().setNeedInitialFocus(true);   // 当webView调用RequestFocus时为WebView设置节点
        webView.getSettings().setTextZoom(100);
        webView.getSettings().setSupportZoom(true);   // 支持缩放
        webView.getSettings().setBuiltInZoomControls(true);  // 出现缩放工具
        webView.getSettings().setDisplayZoomControls(false);  // 隐藏原生的缩放控件
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);  // 支持内容重新布局
        webView.getSettings().setUseWideViewPort(true);   // 将图片调整到适合WebView的大小
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowFileAccess(true);   // 可以访问文件
        webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);   // 自适应屏幕
        webView.getSettings().setLoadWithOverviewMode(true);// 缩放至屏幕大小
        webView.getSettings().setBlockNetworkImage(false);
        webView.getSettings().setLoadsImagesAutomatically(true); // 自动加载图片
        webView.setWebViewClient(new WebViewClient());

        // WebView 设置实现两个手指缩放网页：
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);

        // 清理cache 和历史记录的方法：da
        webView.clearCache(true);
        webView.clearHistory();
        webView.loadUrl("http://www.baidu.com/");
    }

    /**
     * 设置点击返回按钮，跳转到上一个html页面，而不是退出当前activity
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                //获取webView的浏览记录
                WebBackForwardList mWebBackForwardList = webView.copyBackForwardList();
                //这里的判断是为了让页面在有上一个页面的情况下，跳转到上一个html页面，而不是退出当前activity
                if (mWebBackForwardList.getCurrentIndex() > 0) {
                    String historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex() - 1).getUrl();
                    if (!historyUrl.equals("http://www.baidu.com/")) {
                        webView.goBack();
                        return true;
                    }
                }
            } else {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        AlertDialog.Builder build = new AlertDialog.Builder(this);
                        build.setTitle("注意")
                                .setMessage("确定要退出吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        finish();

                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub

                                    }
                                })
                                .show();
                        break;

                    default:
                        break;
                }
                return false;

            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
