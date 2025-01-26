package dev.gmarques.remotewincontrol;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import dev.gmarques.remotewincontrol.databinding.BottomSheetVerAcoesBindingImpl;
import dev.gmarques.remotewincontrol.databinding.DialogoDesligarBindingImpl;
import dev.gmarques.remotewincontrol.databinding.DialogoEditarAcaoBindingImpl;
import dev.gmarques.remotewincontrol.databinding.DialogoGravarAcoesBindingImpl;
import dev.gmarques.remotewincontrol.databinding.DialogoIpPortaBindingImpl;
import dev.gmarques.remotewincontrol.databinding.FragmentMainBindingImpl;
import dev.gmarques.remotewincontrol.databinding.ItemAcaoBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_BOTTOMSHEETVERACOES = 1;

  private static final int LAYOUT_DIALOGODESLIGAR = 2;

  private static final int LAYOUT_DIALOGOEDITARACAO = 3;

  private static final int LAYOUT_DIALOGOGRAVARACOES = 4;

  private static final int LAYOUT_DIALOGOIPPORTA = 5;

  private static final int LAYOUT_FRAGMENTMAIN = 6;

  private static final int LAYOUT_ITEMACAO = 7;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(7);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(dev.gmarques.remotewincontrol.R.layout.bottom_sheet_ver_acoes, LAYOUT_BOTTOMSHEETVERACOES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(dev.gmarques.remotewincontrol.R.layout.dialogo_desligar, LAYOUT_DIALOGODESLIGAR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(dev.gmarques.remotewincontrol.R.layout.dialogo_editar_acao, LAYOUT_DIALOGOEDITARACAO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(dev.gmarques.remotewincontrol.R.layout.dialogo_gravar_acoes, LAYOUT_DIALOGOGRAVARACOES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(dev.gmarques.remotewincontrol.R.layout.dialogo_ip_porta, LAYOUT_DIALOGOIPPORTA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(dev.gmarques.remotewincontrol.R.layout.fragment_main, LAYOUT_FRAGMENTMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(dev.gmarques.remotewincontrol.R.layout.item_acao, LAYOUT_ITEMACAO);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_BOTTOMSHEETVERACOES: {
          if ("layout/bottom_sheet_ver_acoes_0".equals(tag)) {
            return new BottomSheetVerAcoesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for bottom_sheet_ver_acoes is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGODESLIGAR: {
          if ("layout/dialogo_desligar_0".equals(tag)) {
            return new DialogoDesligarBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialogo_desligar is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGOEDITARACAO: {
          if ("layout/dialogo_editar_acao_0".equals(tag)) {
            return new DialogoEditarAcaoBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialogo_editar_acao is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGOGRAVARACOES: {
          if ("layout/dialogo_gravar_acoes_0".equals(tag)) {
            return new DialogoGravarAcoesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialogo_gravar_acoes is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGOIPPORTA: {
          if ("layout/dialogo_ip_porta_0".equals(tag)) {
            return new DialogoIpPortaBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialogo_ip_porta is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMAIN: {
          if ("layout/fragment_main_0".equals(tag)) {
            return new FragmentMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_main is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMACAO: {
          if ("layout/item_acao_0".equals(tag)) {
            return new ItemAcaoBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_acao is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(7);

    static {
      sKeys.put("layout/bottom_sheet_ver_acoes_0", dev.gmarques.remotewincontrol.R.layout.bottom_sheet_ver_acoes);
      sKeys.put("layout/dialogo_desligar_0", dev.gmarques.remotewincontrol.R.layout.dialogo_desligar);
      sKeys.put("layout/dialogo_editar_acao_0", dev.gmarques.remotewincontrol.R.layout.dialogo_editar_acao);
      sKeys.put("layout/dialogo_gravar_acoes_0", dev.gmarques.remotewincontrol.R.layout.dialogo_gravar_acoes);
      sKeys.put("layout/dialogo_ip_porta_0", dev.gmarques.remotewincontrol.R.layout.dialogo_ip_porta);
      sKeys.put("layout/fragment_main_0", dev.gmarques.remotewincontrol.R.layout.fragment_main);
      sKeys.put("layout/item_acao_0", dev.gmarques.remotewincontrol.R.layout.item_acao);
    }
  }
}
