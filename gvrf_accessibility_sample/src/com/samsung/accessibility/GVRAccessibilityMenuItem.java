package com.samsung.accessibility;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRRenderData.GVRRenderingOrder;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRTexture;

import com.samsung.accessibility.focus.FocusableSceneObject;
import com.samsung.accessibility.focus.OnFocusListener;

final class GVRAccessibilityMenuItem extends GVRAccessibilityInteractiveObject {

    private GVRContext mGvrContext;
    public GVRSceneObject backIcon;
    private static final int IN_FOCUS_COLOR = 8570046;
    private static final int LOST_FOCUS_COLOR = 6186095;
    private static final int CLICKED_COLOR = 12631476;
    private boolean clicked;

    public GVRAccessibilityMenuItem(GVRContext gvrContext, GVRTexture iconMenu) {
        super(gvrContext);
        mGvrContext = gvrContext;
        GVRMesh mSlotMesh = gvrContext.loadMesh(new GVRAndroidResource(gvrContext, R.raw.circle_menu));
        GVRTexture mSpacerTexture = gvrContext.loadTexture(new GVRAndroidResource(gvrContext, R.drawable.circle_normal_alpha));
        GVRMaterial material = new GVRMaterial(gvrContext);
        GVRRenderData renderData = new GVRRenderData(gvrContext);
        renderData.setMaterial(material);
        renderData.setMesh(mSlotMesh);
        attachRenderData(renderData);
        attachEyePointeeHolder();
        this.getRenderData().getMaterial().setMainTexture(mSpacerTexture);
        setFocus(true);
        GVRAccessibilityMenuItem.this.getRenderData().getMaterial().setColor(LOST_FOCUS_COLOR);
        focusAndUnFocus();
        createIcon(iconMenu);
    }

    private void focusAndUnFocus() {
        setOnFocusListener(new OnFocusListener() {

            @Override
            public void lostFocus(FocusableSceneObject object) {
                if (clicked)
                    GVRAccessibilityMenuItem.this.getRenderData().getMaterial().setColor(CLICKED_COLOR);
                else
                    GVRAccessibilityMenuItem.this.getRenderData().getMaterial().setColor(LOST_FOCUS_COLOR);
            }

            @Override
            public void inFocus(FocusableSceneObject object) {
                if (clicked)
                    GVRAccessibilityMenuItem.this.getRenderData().getMaterial().setColor(CLICKED_COLOR);

            }

            @Override
            public void gainedFocus(FocusableSceneObject object) {
                GVRAccessibilityMenuItem.this.getRenderData().getMaterial().setColor(IN_FOCUS_COLOR);
            }
        });
    }

    private void createIcon(GVRTexture iconMenu) {
        backIcon = new GVRSceneObject(mGvrContext, mGvrContext.createQuad(.60f, .20f), iconMenu);
        backIcon.getTransform().setPosition(-0f, 0.02f, -0.7f);
        backIcon.getTransform().rotateByAxis(-90, 1, 0, 0);
        backIcon.getTransform().rotateByAxisWithPivot(245, 0, 1, 0, 0, 0, 0);
        backIcon.getRenderData().setRenderingOrder(GVRRenderingOrder.OVERLAY);

        addChildObject(backIcon);
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public boolean isClicked() {
        return this.clicked;
    }

}
