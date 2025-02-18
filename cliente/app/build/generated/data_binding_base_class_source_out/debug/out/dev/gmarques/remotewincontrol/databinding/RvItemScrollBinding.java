// Generated by view binder compiler. Do not edit!
package dev.gmarques.remotewincontrol.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import dev.gmarques.remotewincontrol.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RvItemScrollBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final CardView indicadorDestaque;

  private RvItemScrollBinding(@NonNull ConstraintLayout rootView,
      @NonNull CardView indicadorDestaque) {
    this.rootView = rootView;
    this.indicadorDestaque = indicadorDestaque;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RvItemScrollBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RvItemScrollBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.rv_item_scroll, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RvItemScrollBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.indicador_destaque;
      CardView indicadorDestaque = ViewBindings.findChildViewById(rootView, id);
      if (indicadorDestaque == null) {
        break missingId;
      }

      return new RvItemScrollBinding((ConstraintLayout) rootView, indicadorDestaque);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
