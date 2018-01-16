package witch.image.hdy.im.image_switch;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RollPagerView2 banner;
    private TextView textView;
    private int muisic_index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
//        isGrantExternalRW();
        banner = (RollPagerView2) this.findViewById(R.id.banner);
        banner.setAnimationDurtion(1500);
        textView = (TextView) this.findViewById(R.id.textView);
        final ArrayList<Pic> pics = getPic();
        if (pics != null && pics.size() != 0) {
            textView.setText(pics.get(0).getName());
        }
        banner.setAdapter(new TestNomalAdapter(pics), new PageChangeListner() {
            @Override
            public void onChange(int postion) {
//                System.out.println(postion);
                if (pics != null && pics.size() != 0) {
                    textView.setText(pics.get(postion).getName());
                }
            }
        });
        playMusic();
    }

    public void playMusic() {
        final File path = getSDPath();
        if (path.exists()) {
            File file = new File(path.getAbsolutePath() + File.separator + "picSave");
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
            }
            String[] list = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File pathname, String name) {
                    String upperCase = name.toUpperCase();
                    if (upperCase.endsWith(".MP3") || upperCase.endsWith(".WMV")) {
                        return true;
                    }
                    return false;
                }
            });
            System.out.println("list-size:" + list.length);
            if (list.length > 0) {
                play(path, list);
            }

//            return pics;
        }
//        return null;
    }

    private void play(final File path, final String[] list) {
        if (muisic_index < list.length - 1) {
            muisic_index++;
        } else {
            muisic_index = 0;
        }
        MediaPlayer mp = new MediaPlayer();
//                MediaPlayer mp = MediaPlayer.create(this, null);
        try {
            mp.setDataSource(path.getAbsolutePath() + File.separator + "picSave" + File.separator + list[muisic_index]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                play(path, list);
            }
        });
    }

    public ArrayList<Pic> getPic() {
        File path = getSDPath();
        ArrayList<Pic> pics = new ArrayList<>();
        if (path.exists()) {
            File file = new File(path.getAbsolutePath() + File.separator + "picSave");
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
            }
            String[] list = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File pathname, String name) {
                    String upperCase = name.toUpperCase();
                    if (upperCase.endsWith(".JPG") || upperCase.endsWith(".JPEG") || upperCase.endsWith(".BMP") || upperCase.endsWith(".PNG")) {
                        return true;
                    }
                    return false;
                }
            });
            for (String s : list) {
                int indexOf = s.lastIndexOf(".");
                String substring = s.substring(0, indexOf);
                Pic pic = new Pic(substring, s);
                pics.add(pic);
            }
            return pics;
        }
        return null;
    }

    private class TestNomalAdapter extends StaticPagerAdapter {

        private String[] imgs = null;
        private int[] imgs2 = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5};

        public TestNomalAdapter(ArrayList<Pic> pics) {
            if (pics != null && pics.size() != 0) {
                imgs = new String[pics.size()];
                for (int i = 0; i < pics.size(); i++) {
                    imgs[i] = pics.get(i).getUrl();
                }
            }

        }

        @Override
        public View getView(ViewGroup container, int position) {
//            System.out.println("sadasd");
            ImageView view = new ImageView(container.getContext());
            if (imgs == null) {
                view.setImageResource(imgs2[position]);
            } else {
                File path = getSDPath();
                view.setImageBitmap(ImageUtils.getSmallBitmap(path.getAbsolutePath() + File.separator + "picSave" + File.separator + imgs[position], 1920, 1080));
//                view.setImageDrawable(Drawable.createFromPath(path.getAbsolutePath() + File.separator + "picSave" + File.separator + imgs[position]));
            }
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            if (imgs == null) {
                return imgs2.length;
            } else {
                return imgs.length;
            }
        }
    }

    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    public File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir;
    }

//    public boolean isGrantExternalRW() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && MainActivity.this.checkSelfPermission(
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//            this.requestPermissions(new String[]{
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//            }, 1);
//
//            return false;
//        }
//
//        return true;
//    }
}
