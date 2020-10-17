package nl.invissvenska.pulsatingripple;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
       final PulsatingLayout pl = view.findViewById(R.id.ripple);
        pl.startRippleAnimation();

//        final Handler handler=new Handler();


//        ImageView button=view.findViewById(R.id.centerImage);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pl.startRippleAnimation();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        foundDevice();
//                    }
//                },3000);
//            }
//        });

    }

//    private void foundDevice(){
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.setDuration(400);
//        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
//        ArrayList<Animator> animatorList=new ArrayList<Animator>();
//        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleX", 0f, 1.2f, 1f);
//        animatorList.add(scaleXAnimator);
//        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleY", 0f, 1.2f, 1f);
//        animatorList.add(scaleYAnimator);
//        animatorSet.playTogether(animatorList);
//        foundDevice.setVisibility(View.VISIBLE);
//        animatorSet.start();
//    }
}