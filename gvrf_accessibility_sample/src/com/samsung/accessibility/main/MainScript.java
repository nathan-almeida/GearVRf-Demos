package com.samsung.accessibility.main;

import java.util.Locale;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRScript;
import org.gearvrf.GVRTexture;
import org.gearvrf.accessibility.GVRAccessibilityTalkBack;

import android.util.Log;
import android.view.MotionEvent;

import com.samsung.accessibility.R;
import com.samsung.accessibility.focus.FocusableController;
import com.samsung.accessibility.focus.FocusableSceneObject;
import com.samsung.accessibility.focus.OnFocusListener;
import com.samsung.accessibility.gaze.GazeCursorSceneObject;
import com.samsung.accessibility.scene.AccessibilityScene;
import com.samsung.accessibility.shortcut.ShortcutMenu;
import com.samsung.accessibility.shortcut.ShortcutMenuItem;
import com.samsung.accessibility.shortcut.ShortcutMenuItem.TypeItem;
import com.samsung.accessibility.util.AccessibilityManager;
import com.samsung.accessibility.util.AccessibilityTexture;

public class MainScript extends GVRScript {

    private static GVRContext mGVRContext;

    private GazeCursorSceneObject cursor;
    public static AccessibilityScene accessibilityScene;
    public static AccessibilityManager manager;

    @Override
    public void onInit(final GVRContext gvrContext) {
        mGVRContext = gvrContext;
        AccessibilityTexture.getInstance(gvrContext);
        cursor = GazeCursorSceneObject.getInstance(gvrContext);
        manager = new AccessibilityManager(gvrContext);
        ShortcutMenu shortcutMenu = createShortCut();
        accessibilityScene = new AccessibilityScene(gvrContext, gvrContext.getMainScene(), shortcutMenu);
        for (GVRSceneObject object : accessibilityScene.getWholeSceneObjects()) {
            if (object.getRenderData() != null && object.getRenderData().getMaterial() != null) {
                object.getRenderData().getMaterial().setOpacity(0);
            }
        }
        gvrContext.getMainScene().getMainCameraRig().addChildObject(cursor);
        GVRSceneObject skybox = createSkybox();
        skybox.getRenderData().setRenderingOrder(0);
        gvrContext.getMainScene().addSceneObject(skybox);

        createObjectTalkBack();
        createObject1TalkBack();
        createObject2TalkBack();
        mGVRContext.getMainScene().addSceneObject(shortcutMenu);
    }

    private ShortcutMenu createShortCut() {
        ShortcutMenu shortcuteMenu = new ShortcutMenu(mGVRContext);
        ShortcutMenuItem shortcuteItem = shortcuteMenu.getShortcutItems().get(0);
        shortcuteItem.createIcon(AccessibilityTexture.getInstance(mGVRContext).getAccessibilityIcon(), TypeItem.ACCESSIBILITY);
        return shortcuteMenu;
    }

    private void createObjectTalkBack() {

        FocusableSceneObject object = new FocusableSceneObject(mGVRContext, mGVRContext.createQuad(.5f, .5f),
                mGVRContext.loadTexture(new GVRAndroidResource(mGVRContext,
                        R.drawable.skybox_accessibility)));

        GVRAccessibilityTalkBack talkBack = new GVRAccessibilityTalkBack(Locale.US, mGVRContext.getActivity(), "Object");
        object.getTransform().setPosition(-1, 0, -1);
        object.attachEyePointeeHolder();
        object.setTalkBack(talkBack);
        object.setOnFocusListener(new OnFocusListener() {

            @Override
            public void lostFocus(FocusableSceneObject object) {

            }

            @Override
            public void inFocus(FocusableSceneObject object) {
                // TODO Auto-generated method stub

            }

            @Override
            public void gainedFocus(FocusableSceneObject object) {
                object.getTalkBack().speak();
            }
        });
        mGVRContext.getMainScene().addSceneObject(object);
    }

    private void createObject1TalkBack() {

        final FocusableSceneObject object1 = new FocusableSceneObject(mGVRContext, mGVRContext.createQuad(.5f, .5f),
                mGVRContext.loadTexture(new GVRAndroidResource(mGVRContext,
                        R.drawable.skybox_accessibility)));
        GVRAccessibilityTalkBack talkBack = new GVRAccessibilityTalkBack(Locale.US, mGVRContext.getActivity(), "Object 2");
        object1.getTransform().setPosition(1, 0, -1);
        object1.attachEyePointeeHolder();
        object1.setTalkBack(talkBack);
        object1.setOnFocusListener(new OnFocusListener() {

            @Override
            public void lostFocus(FocusableSceneObject object) {
            }

            @Override
            public void inFocus(FocusableSceneObject object) {
                // TODO Auto-generated method stub

            }

            @Override
            public void gainedFocus(FocusableSceneObject object) {
                object1.getTalkBack().speak();
                Log.e("test", "gained focus");

            }
        });
        mGVRContext.getMainScene().addSceneObject(object1);
    }

    private void createObject2TalkBack() {
        final FocusableSceneObject object = new FocusableSceneObject(mGVRContext, mGVRContext.createQuad(.5f, .5f),
                mGVRContext.loadTexture(new GVRAndroidResource(mGVRContext,
                        R.drawable.skybox_accessibility)));

        GVRAccessibilityTalkBack talkBack = new GVRAccessibilityTalkBack(Locale.US, mGVRContext.getActivity(), "Object 3");
        object.getTransform().setPosition(0, 0, -1);
        object.attachEyePointeeHolder();
        object.setTalkBack(talkBack);
        object.setOnFocusListener(new OnFocusListener() {

            @Override
            public void lostFocus(FocusableSceneObject object) {

            }

            @Override
            public void inFocus(FocusableSceneObject object) {
                // TODO Auto-generated method stub

            }

            @Override
            public void gainedFocus(FocusableSceneObject object) {
                object.getTalkBack().speak();
            }
        });
        mGVRContext.getMainScene().addSceneObject(object);
    }

    private GVRSceneObject createSkybox() {

        GVRMesh mesh = mGVRContext.loadMesh(new GVRAndroidResource(mGVRContext,
                R.raw.skybox_esphere_acessibility));
        GVRTexture texture = mGVRContext.loadTexture(new GVRAndroidResource(
                mGVRContext, R.drawable.skybox_accessibility));
        GVRSceneObject skybox = new GVRSceneObject(mGVRContext, mesh, texture);
        skybox.getTransform().rotateByAxisWithPivot(-90, 1, 0, 0, 0, 0, 0);
        skybox.getTransform().setPositionY(-1.6f);
        skybox.getRenderData().setRenderingOrder(0);

        // applyShaderOnSkyBox(skybox);

        return skybox;
    }

    // private void applyShaderOnSkyBox(GVRSceneObject skyBox) {
    // GVRAccessibilitySceneShader shader = new
    // GVRAccessibilitySceneShader(mGVRContext);
    // skyBox.getRenderData().getMaterial().setShaderType(shader.getShaderId());
    // skyBox.getRenderData().getMaterial().setTexture(GVRAccessibilitySceneShader.TEXTURE_KEY,
    // skyBox.getRenderData().getMaterial().getMainTexture());
    // skyBox.getRenderData().getMaterial().setFloat(GVRAccessibilitySceneShader.BLUR_INTENSITY,
    // 1);
    // }

    @Override
    public SplashMode getSplashMode() {
        return SplashMode.NONE;
    }

    @Override
    public void onStep() {
        FocusableController.process(mGVRContext);
    }

    public void onSingleTap(MotionEvent e) {
        FocusableController.clickProcess(mGVRContext);
    }
}
