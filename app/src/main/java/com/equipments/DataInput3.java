package com.equipments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.equipments.GettersSetters.InpectionId;
import com.equipments.Utils.DBConstant;
import com.equipments.Utils.Dbhandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Administrator on 10/06/2016.
 */
public class DataInput3 extends Fragment implements View.OnClickListener{

    ImageButton btnCamera;
    private String mCurrentPhotoPath;
    Bitmap processedBitmap;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView img,imgThumb[] ;
    RelativeLayout bar;
    EditText imdesc;
    String Id;
    static int img_cnt = 0;
    LinearLayout imagelayout;
    FancyButton btnSave,btnCam,btnCancel;
    Dbhandler db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_datainput3, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        initialize(view);
        Id= InpectionId.getId();
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(processedBitmap!=null)
                new Save_Photo_Async_Task().execute();
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    void initialize(View view)
    {
        btnCamera=(ImageButton) view.findViewById(R.id.btnCamera);

        db=new Dbhandler(getActivity());
        bar=(RelativeLayout)view.findViewById(R.id.loadingPane);
        bar.setVisibility(View.GONE);

        img = (ImageView) view.findViewById(R.id.Snap);
        mAlbumStorageDirFactory = new BaseAlbumDirFactory();

        imdesc=(EditText) view.findViewById(R.id.desc);
        imgThumb=new ImageView[DBConstant.C_No_of_Image];
        imagelayout=(LinearLayout) view.findViewById(R.id.imagelayout);

        btnSave=(FancyButton) view.findViewById(R.id.btnSave);
       /* btnCam=(FancyButton) view.findViewById(R.id.btnCam);
        btnCancel=(FancyButton) view.findViewById(R.id.btnCancel);*/

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        setPhotoFrame();
        processedBitmap=compressImage();

    }

    private Bitmap handleSmallCameraPhoto() {
        int targetW = img.getWidth();
        int targetH = img.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        if(bitmap!=null) {
            img.setImageBitmap(bitmap);
            img.setVisibility(View.VISIBLE);
        }
        return bitmap;
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private String getAlbumName() {
        return "Equipments";
    }

    void setPhotoFrame()
    {

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        if(bitmap!=null) {
            img.setImageBitmap(bitmap);
            img.setVisibility(View.VISIBLE);
        }
    }
    public Bitmap compressImage()
    {
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(mCurrentPhotoPath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 500.0f;
        float maxWidth = 250.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(mCurrentPhotoPath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {

        }
        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(mCurrentPhotoPath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return scaledBitmap;
    }

    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap)     {
            inSampleSize++;
        }
        return inSampleSize;
    }

    public void setThumbnail()
    {
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        imgThumb[img_cnt]=new ImageView(getActivity());
        imgThumb[img_cnt].setOnClickListener(this);
        imgThumb[img_cnt].setId(img_cnt+1);
        lp.setMargins(2,2,2,2);
        imagelayout.addView(imgThumb[img_cnt],lp);
        Bitmap scaled=null;
        if(processedBitmap.getWidth()>processedBitmap.getHeight()) {
            scaled = Bitmap.createScaledBitmap(processedBitmap, 150, 100, true);
        }else
        {
            scaled = Bitmap.createScaledBitmap(processedBitmap, 100, 150, true);
        }
        imgThumb[img_cnt].setImageBitmap(scaled);
        img_cnt++;
    }

    @Override
    public void onClick(View v) {
        Bitmap bm=db.getImage(v.getId(),Id);
        if(bm!=null)
            loadPhoto(bm,v);
        else
            Snackbar.make(v,"Save Image First !!!",Snackbar.LENGTH_SHORT).show();
    }
    private void loadPhoto(Bitmap bitmap,View v) {

        AlertDialog.Builder imageDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.dialog,
                (ViewGroup) v.findViewById(R.id.layout_root));
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        image.setImageBitmap(bitmap);
        imageDialog.setView(layout);
        imageDialog.setPositiveButton("Close", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        imageDialog.create();
        imageDialog.show();
    }


    private Boolean save_photo()
    {
        ContentValues cv=new ContentValues();
        cv.put(DBConstant.C_ID, Id);
        cv.put(DBConstant.C_Image, imgConversion(processedBitmap));
        cv.put(DBConstant.C_Image_Id,img_cnt+1);
        cv.put(DBConstant.C_Image_Desc, imdesc.getText().toString());
        return db.saveimg(cv);
    }
    private String imgConversion(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private class Save_Photo_Async_Task extends AsyncTask<Void,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return save_photo();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            bar.setVisibility(View.GONE);
            if(result)
            {
                Toast.makeText(getActivity(),"Image Saved!!!",Toast.LENGTH_SHORT).show();
                setThumbnail();
                img.setImageResource(0);
                imdesc.setText("");
            }
            else
            {
                Toast.makeText(getActivity(),"Failed!!!",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }
}

