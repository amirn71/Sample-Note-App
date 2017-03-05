package com.note.amirehsan.samplenoteapp.Activityes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.note.amirehsan.samplenoteapp.Database.Note_Contact;
import com.note.amirehsan.samplenoteapp.Database.Note_DatabaseHandler;
import com.note.amirehsan.samplenoteapp.Units.Persian_date;
import com.note.amirehsan.samplenoteapp.R;
import java.io.ByteArrayOutputStream;


/**
 * Created by AMiR ehsan on 2/27/2017.
 */

public class Activity_Drawing extends AppCompatActivity {

    private Paint mPaint;
    private int opacity = 255;
    public static byte[] byteArray, byteArray2;
    FrameLayout v;
    Bitmap signature;
    Bitmap bitmapm;
    public static String str;
    EditText title;
    String wrineNoteCategoryName, toolbarColor;
    String exist_id = "null";
    DrawingView myView;
    Typeface fontAwesome;
    boolean exist = false;
    Note_DatabaseHandler db;
    Button erase, draw, red, blue, yellow;
    public byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initializing_contents();

        // Receiving the Data
        Intent intent = getIntent();
        toolbarColor = intent.getStringExtra("color");
        wrineNoteCategoryName = intent.getStringExtra("category");
        exist_id = intent.getStringExtra("id");

        if (exist_id.equals("null")) {
            exist = false;
        } else if (!exist_id.equals("null")) {
            exist = true;
            title.setText(intent.getStringExtra("title"));
            bytes = android.util.Base64.decode(intent.getStringExtra("data"), android.util.Base64.NO_WRAP);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bitmapm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setFilterBitmap(true);
                    paint.setDither(true);
                    myView.mCanvas.drawBitmap(bitmapm, 0, 0, paint);
                    v.removeView(myView);
                    v.addView(myView);
                }
            }, 100);
        }

        if (toolbarColor.equals("green")) {
            toolbar.setBackgroundResource(R.color.green);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.parseColor("#469f4a"));
            }

        } else if (toolbarColor.equals("red")) {
            toolbar.setBackgroundResource(R.color.red);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.parseColor("#df3d31"));
            }

        } else if (toolbarColor.equals("blue")) {

            toolbar.setBackgroundResource(R.color.blue);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.parseColor("#1d8be2"));
            }

        } else if (toolbarColor.equals("purple")) {
            toolbar.setBackgroundResource(R.color.purple);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.parseColor("#8c239e"));
            }
        }

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
                opacity = getOpacity();
                mPaint.setAlpha(opacity);
                mPaint.setStrokeWidth(20);
                mPaint.setColor(Color.WHITE);

            }
        });


        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
                mPaint.setXfermode(null);
                mPaint.setAlpha(0xFF);
                mPaint.setStrokeWidth(4);
                mPaint.setColor(Color.BLACK);

            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
                mPaint.setXfermode(null);
                mPaint.setAlpha(0xFF);
                mPaint.setStrokeWidth(4);
                mPaint.setColor(Color.RED);
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
                mPaint.setXfermode(null);
                mPaint.setAlpha(0xFF);
                mPaint.setStrokeWidth(4);
                mPaint.setColor(Color.BLUE);
            }
        });

        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
                mPaint.setXfermode(null);
                mPaint.setAlpha(0xFF);
                mPaint.setStrokeWidth(4);
                mPaint.setColor(Color.YELLOW);
            }
        });

    }

    public class DrawingView extends View {

        private Bitmap mBitmap;
        public Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

        public DrawingView(Context c) {
            super(c);

            context = c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLUE);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);

        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            super.onDraw(canvas);

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
            canvas.drawPath(circlePath, circlePaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;
        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double activity_draw
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                if (!exist) {
                    Time now = new Time();
                    now.setToNow();
                    myView.buildDrawingCache();
                    signature = myView.getDrawingCache();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    signature.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                    str = android.util.Base64.encodeToString(byteArray, android.util.Base64.NO_WRAP);
                    myView.destroyDrawingCache();
                    Note_Contact noteContact = new Note_Contact(title.getText().toString(),
                            wrineNoteCategoryName, str,
                            Persian_date.getCurrentShamsidate() + " - " + now.format("%k:%M"),
                            toolbarColor, "yes");
                    db.addContact(noteContact);
                    Intent nextScreen = new Intent(this, Activity_Add_Note.class);
                    nextScreen.putExtra("color", toolbarColor);
                    nextScreen.putExtra("category", wrineNoteCategoryName);
                    startActivity(nextScreen);
                    finish();
                } else if (exist) {
                    Time now = new Time();
                    now.setToNow();
                    myView.buildDrawingCache();
                    signature = myView.getDrawingCache();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    signature.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                    str = android.util.Base64.encodeToString(byteArray, android.util.Base64.NO_WRAP);
                    myView.destroyDrawingCache();
                    Note_Contact noteContactFromID = db.getContact(Integer.parseInt(exist_id));
                    Note_Contact noteContact = new Note_Contact(noteContactFromID.getID(), title.getText().toString(),
                            wrineNoteCategoryName, str,
                            Persian_date.getCurrentShamsidate() + " - " + now.format("%k:%M"),
                            toolbarColor, "yes");
                    db.updateContact(noteContact);
                    Intent nextScreen = new Intent(this, Activity_Add_Note.class);
                    nextScreen.putExtra("color", toolbarColor);
                    nextScreen.putExtra("category", wrineNoteCategoryName);
                    startActivity(nextScreen);
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public int getOpacity() {
        return this.opacity;
    }

    public void initializing_contents() {

        v = (FrameLayout) findViewById(R.id.drawing_layout);
        myView = new DrawingView(this);
        v.addView(myView);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(4);
        fontAwesome = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");
        erase = (Button) findViewById(R.id.erase);
        draw = (Button) findViewById(R.id.draw);
        red = (Button) findViewById(R.id.red);
        blue = (Button) findViewById(R.id.blue);
        yellow = (Button) findViewById(R.id.yellow);
        erase.setTypeface(fontAwesome);
        draw.setTypeface(fontAwesome);
        red.setTypeface(fontAwesome);
        blue.setTypeface(fontAwesome);
        yellow.setTypeface(fontAwesome);
        title = (EditText) findViewById(R.id.title);
        title.setText("");
        db = new Note_DatabaseHandler(this);
    }

    @Override
    public void onBackPressed() {

        if (!exist) {
            Time now = new Time();
            now.setToNow();
            myView.buildDrawingCache();
            signature = myView.getDrawingCache();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            signature.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
            str = android.util.Base64.encodeToString(byteArray, android.util.Base64.NO_WRAP);
            myView.destroyDrawingCache();
            Note_Contact noteContact = new Note_Contact(title.getText().toString(),
                    wrineNoteCategoryName, str,
                    Persian_date.getCurrentShamsidate() + " - " + now.format("%k:%M"),
                    toolbarColor, "yes");
            db.addContact(noteContact);
            Intent nextScreen = new Intent(this, Activity_Add_Note.class);
            nextScreen.putExtra("color", toolbarColor);
            nextScreen.putExtra("category", wrineNoteCategoryName);
            startActivity(nextScreen);
            finish();
        } else if (exist) {
            Time now = new Time();
            now.setToNow();
            myView.buildDrawingCache();
            signature = myView.getDrawingCache();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            signature.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
            str = android.util.Base64.encodeToString(byteArray, android.util.Base64.NO_WRAP);
            myView.destroyDrawingCache();
            Note_Contact noteContactFromID = db.getContact(Integer.parseInt(exist_id));
            Note_Contact noteContact = new Note_Contact(noteContactFromID.getID(), title.getText().toString(),
                    wrineNoteCategoryName, str,
                    Persian_date.getCurrentShamsidate() + " - " + now.format("%k:%M"),
                    toolbarColor, "yes");
            db.updateContact(noteContact);
            Intent nextScreen = new Intent(this, Activity_Add_Note.class);
            nextScreen.putExtra("color", toolbarColor);
            nextScreen.putExtra("category", wrineNoteCategoryName);
            startActivity(nextScreen);
            finish();
        }
    }
}