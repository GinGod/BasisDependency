package com.gingold.basisdependency.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basisdependency.data.Urls;
import com.gingold.basisdependency.utils.ImageUploadUtils;
import com.gingold.basislibrary.okhttp.BasisCallback;
import com.gingold.basislibrary.okhttp.BasisDownloadCallback;
import com.gingold.basislibrary.okhttp.BasisOkHttpUtils;
import com.gingold.basislibrary.utils.BasisLogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 派件发件图片上传
 */

public class OkHttpPicActivity extends BaseActivity {
    private RadioGroup rg_uploadpic;
    private ImageView iv_uploadpic;
    private EditText et_uploadpic_scanNo;
    private TextView tv_uploadpic_save;
    private TextView tv_uploadpic_download;
    private String uploadType = "0";
    public String picFileFullName;
    public String imagePath;
    public OkHttpClient mOkHttpClient;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_uploadpic);
        initTitle("上传图片", "");
    }

    @Override
    public void initView() {
        rg_uploadpic = (RadioGroup) findViewById(R.id.rg_uploadpic);
        iv_uploadpic = findImageView(R.id.iv_uploadpic);
        et_uploadpic_scanNo = findEditText(R.id.et_uploadpic_scanNo);
        tv_uploadpic_save = findTextView(R.id.tv_uploadpic_save);
        tv_uploadpic_download = findTextView(R.id.tv_uploadpic_download);
    }

    @Override
    public void listener() {
        rg_uploadpic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_uploadpic_paijian:
                        uploadType = "0";
                        break;
                    case R.id.rb_uploadpic_qujian:
                        uploadType = "1";
                        break;
                }
            }
        });
    }

    @Override
    public void logicDispose() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_uploadpic:
                TakePhoto();
                break;
            case R.id.tv_uploadpic_save:
                if (isNotEmpty(imagePath, "请拍照后再上传图片"))
                    upload();
//                    upload1();
                break;
            case R.id.tv_uploadpic_download:
                download1();
                break;
        }

    }

    private void download1() {
//        BasisProgressDialogUtils.build(mActivity).show();
        String url = "http://img.juimg.com/tuku/yulantu/120926/219049-12092612154377.jpg";
        url = Urls.picUrl2;

        BasisOkHttpUtils.download().get().dialog(mActivity).url(url).fileName(null).build().execute(new BasisDownloadCallback() {
            @Override
            public void onSuccess(Call call, Response response, String filePath) {
                toast(filePath);
            }

            @Override
            public void onProgress(long totalSize, long currentSize, long onProgress) {
//                BasisLogUtils.e("onProgress: " + totalSize + " " + currentSize + " " + onProgress);
            }

            @Override
            public void onFailure(String url, String content, Call call, Exception e, String message) {

            }

            @Override
            public void onException(String url, String content, String result, Exception e, String errorMessage) {

            }
        });
    }

    private void download() {
        String url = "http://expapi.tswlsys.com:80/api/file/2017/8/22/d772901344b04b7385f732047bbf185d_1503381458183.jpg";
        mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    String sdPadth = Environment.getExternalStorageDirectory() + "";// 获取SD卡路径
                    String mSavePath = sdPadth + "/Download";
                    File fileDir = new File(mSavePath);
                    if (!fileDir.exists()) {// 判断文件目录是否存在,不存在则创建该目录
                        fileDir.mkdir();
                    }
                    File file = new File(mSavePath, "/name");

                    fileOutputStream = new FileOutputStream(file);

                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                BasisLogUtils.e("文件下载成功");
            }
        });
    }

    /**
     * 拍照
     */
    private void TakePhoto() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
            picFileFullName = outFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, 100);
        }
    }

    /**
     * 从相册选择
     */
    private void PickedPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        this.startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100://拍照
                if (resultCode == RESULT_OK) {
                    setImageView(picFileFullName);
                } else if (resultCode == RESULT_CANCELED) {
                } else {
                    Toast.makeText(getApplicationContext(), "获取图片失败，请重新选取",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case 200:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        String realPath = ImageUploadUtils.getRealPathFromURI(this, uri);
                        setImageView(realPath);
                    } else {
                    }
                }
                break;
        }
    }

    /**
     * 设置图片
     */
    private void setImageView(String realPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        newOpts.inSampleSize = 5;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bmp = BitmapFactory.decodeFile(realPath, newOpts);
        int degree = ImageUploadUtils.readPictureDegree(realPath);
        if (degree <= 0) {
            isCheck(realPath, bmp);
        } else {
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0,
                    bmp.getWidth(), bmp.getHeight(), matrix, true);
            isCheck(realPath, resizedBitmap);
        }
    }

    /**
     * 设置图片
     */
    private void isCheck(String realPath, Bitmap bitmap) {
        iv_uploadpic.setImageBitmap(bitmap);
        imagePath = realPath;
        BasisLogUtils.e("realPath" + realPath);
    }


    private void upload() {
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("WaybillNo", "82223");
        maps.put("people", "57693453");
        maps.put("uploadType", uploadType);//uploadType  //0=派件 1=取件   autoType //0=自动 1=手动
        maps.put("autoType", "0");
        maps.put("DirPath", imagePath);

        String url = "http://10.120.10.78:8041/api-consumer/file/upload";

//        url = "http://img.tswlsys.com/MobileUpLoad.aspx";

        File file = new File(imagePath);
        String name = file.getAbsolutePath().replace("\\", "").replace("/", "");

        BasisOkHttpUtils.initLogState(true)
                .postFile().setLogState(true)
                .url(url)
                .mediaType(null)
//                .addParams(maps)
                .addFile("file", name, file)
                .build()
                .execute(new BasisCallback() {
                    @Override
                    public void onSuccess(Call call, Response response, String result) {
                        toast(result);
                    }

                    @Override
                    public void onFailure(String url, String content, Call call, Exception e, String message) {

                    }

                    @Override
                    public void onException(String url, String content, String result, Exception e, String errorMessage) {

                    }


                });

    }

    private void upload1() {
        String url = "http://10.120.10.78:8041/api-consumer/file/upload";

//        url = "http://img.tswlsys.com/MobileUpLoad.aspx";
        String json = "{\"WaybillNo\":\"82122\",\"people\":\"57693453\",\"uploadType\":\"0\",\"autoType\":\"0\",\"DirPath\":" + imagePath + "}";

        mOkHttpClient = new OkHttpClient();
        File file = new File(imagePath);

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);

        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("file", "file.jpg", fileBody).build();

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        builder.addFormDataPart("param", json);//上传字符串

        builder.addFormDataPart("WaybillNo", "82122");
        builder.addFormDataPart("people", "57693453");
        builder.addFormDataPart("uploadType", uploadType);
        builder.addFormDataPart("autoType", "0");
        builder.addFormDataPart("DirPath", imagePath);
        builder.addFormDataPart("file", "file.jpg", fileBody);

        HashMap<String, String> map = new HashMap<>();
        Set<String> strings = map.keySet();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            BasisLogUtils.e("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

//        builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"file.jpg\""),
//                RequestBody.create(MediaType.parse("image/png"), file));
        RequestBody body = builder.build();


        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                BasisLogUtils.e(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BasisLogUtils.e(response.body().string());
            }
        });
    }
}

