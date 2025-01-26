package dev.gmarques.remotewincontrol.databinding;
import dev.gmarques.remotewincontrol.R;
import dev.gmarques.remotewincontrol.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentMainBindingImpl extends FragmentMainBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(13);
        sIncludes.setIncludes(0, 
            new String[] {"bottom_sheet_ver_acoes"},
            new int[] {1},
            new int[] {dev.gmarques.remotewincontrol.R.layout.bottom_sheet_ver_acoes});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_ping, 2);
        sViewsWithIds.put(R.id.container_status, 3);
        sViewsWithIds.put(R.id.tv_timer, 4);
        sViewsWithIds.put(R.id.mouse_pad, 5);
        sViewsWithIds.put(R.id.mouse_btn_esq, 6);
        sViewsWithIds.put(R.id.centro, 7);
        sViewsWithIds.put(R.id.mouse_btn_meio, 8);
        sViewsWithIds.put(R.id.rv_infinite_scroll, 9);
        sViewsWithIds.put(R.id.mouse_btn_dir, 10);
        sViewsWithIds.put(R.id.fab_acoes, 11);
        sViewsWithIds.put(R.id.fab_voz, 12);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentMainBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }
    private FragmentMainBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.view.View) bindings[7]
            , (android.widget.LinearLayout) bindings[3]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[11]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[12]
            , (dev.gmarques.remotewincontrol.databinding.BottomSheetVerAcoesBinding) bindings[1]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (androidx.cardview.widget.CardView) bindings[10]
            , (androidx.cardview.widget.CardView) bindings[6]
            , (androidx.cardview.widget.CardView) bindings[8]
            , (android.widget.LinearLayout) bindings[5]
            , (androidx.recyclerview.widget.RecyclerView) bindings[9]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[4]
            );
        setContainedBinding(this.includeBottomSheet);
        this.main.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        includeBottomSheet.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (includeBottomSheet.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        includeBottomSheet.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeIncludeBottomSheet((dev.gmarques.remotewincontrol.databinding.BottomSheetVerAcoesBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeIncludeBottomSheet(dev.gmarques.remotewincontrol.databinding.BottomSheetVerAcoesBinding IncludeBottomSheet, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
        executeBindingsOn(includeBottomSheet);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): includeBottomSheet
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}