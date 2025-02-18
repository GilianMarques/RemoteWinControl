// Generated by data binding compiler. Do not edit!
package dev.gmarques.remotewincontrol.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import dev.gmarques.remotewincontrol.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class BottomSheetVerAcoesBinding extends ViewDataBinding {
  @NonNull
  public final Button btnGravar;

  @NonNull
  public final Button btnPararReproducao;

  @NonNull
  public final LinearLayout container;

  @NonNull
  public final ConstraintLayout parentContainer;

  protected BottomSheetVerAcoesBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnGravar, Button btnPararReproducao, LinearLayout container,
      ConstraintLayout parentContainer) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnGravar = btnGravar;
    this.btnPararReproducao = btnPararReproducao;
    this.container = container;
    this.parentContainer = parentContainer;
  }

  @NonNull
  public static BottomSheetVerAcoesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_ver_acoes, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static BottomSheetVerAcoesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<BottomSheetVerAcoesBinding>inflateInternal(inflater, R.layout.bottom_sheet_ver_acoes, root, attachToRoot, component);
  }

  @NonNull
  public static BottomSheetVerAcoesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_ver_acoes, null, false, component)
   */
  @NonNull
  @Deprecated
  public static BottomSheetVerAcoesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<BottomSheetVerAcoesBinding>inflateInternal(inflater, R.layout.bottom_sheet_ver_acoes, null, false, component);
  }

  public static BottomSheetVerAcoesBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static BottomSheetVerAcoesBinding bind(@NonNull View view, @Nullable Object component) {
    return (BottomSheetVerAcoesBinding)bind(component, view, R.layout.bottom_sheet_ver_acoes);
  }
}
