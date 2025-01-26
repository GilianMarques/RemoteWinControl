package dev.gmarques.remotewincontrol.databinding;
import dev.gmarques.remotewincontrol.R;
import dev.gmarques.remotewincontrol.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DialogoEditarAcaoBindingImpl extends DialogoEditarAcaoBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar, 1);
        sViewsWithIds.put(R.id.input_nome, 2);
        sViewsWithIds.put(R.id.edt_nome, 3);
        sViewsWithIds.put(R.id.input_velocidade, 4);
        sViewsWithIds.put(R.id.edt_velocidade, 5);
        sViewsWithIds.put(R.id.btn_remover, 6);
        sViewsWithIds.put(R.id.btn_confirmar_remocao, 7);
        sViewsWithIds.put(R.id.btn_cancelar, 8);
        sViewsWithIds.put(R.id.btn_salvar, 9);
    }
    // views
    @NonNull
    private final androidx.appcompat.widget.LinearLayoutCompat mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DialogoEditarAcaoBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private DialogoEditarAcaoBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[8]
            , (android.widget.Button) bindings[7]
            , (android.widget.Button) bindings[6]
            , (android.widget.Button) bindings[9]
            , (com.google.android.material.textfield.TextInputEditText) bindings[3]
            , (com.google.android.material.textfield.TextInputEditText) bindings[5]
            , (com.google.android.material.textfield.TextInputLayout) bindings[2]
            , (com.google.android.material.textfield.TextInputLayout) bindings[4]
            , (androidx.appcompat.widget.Toolbar) bindings[1]
            );
        this.mboundView0 = (androidx.appcompat.widget.LinearLayoutCompat) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
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
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}