package com.thor.app.gui.fragments.demo;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.databinding.FragmentDemoBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thor.app.auto.common.MusicServiceConnection;
import com.thor.app.gui.adapters.demo.DemoSguRvAdapter;
import com.thor.app.gui.fragments.base.BaseBindingFragment;
import com.thor.app.utils.di.InjectorUtils;
import com.thor.basemodule.extensions.ContextKt;
import com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter;
import com.thor.businessmodule.model.DemoSguPackage;
import com.thor.businessmodule.settings.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.text.Charsets;
import timber.log.Timber;

/* compiled from: DemoSguFragment.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u0000 \u001c2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0002J\b\u0010\u0018\u001a\u00020\u0016H\u0002J\b\u0010\u0019\u001a\u00020\u0016H\u0016J\b\u0010\u001a\u001a\u00020\u0016H\u0002J\b\u0010\u001b\u001a\u00020\u0016H\u0016R.\u0010\u0004\u001a\u001c\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001d"}, d2 = {"Lcom/thor/app/gui/fragments/demo/DemoSguFragment;", "Lcom/thor/app/gui/fragments/base/BaseBindingFragment;", "Lcom/carsystems/thor/app/databinding/FragmentDemoBinding;", "()V", "bindingInflater", "Lkotlin/Function3;", "Landroid/view/LayoutInflater;", "Landroid/view/ViewGroup;", "", "getBindingInflater", "()Lkotlin/jvm/functions/Function3;", "mAdapter", "Lcom/thor/app/gui/adapters/demo/DemoSguRvAdapter;", "mediaPlayer", "Landroid/media/MediaPlayer;", "musicServiceConnection", "Lcom/thor/app/auto/common/MusicServiceConnection;", "getMusicServiceConnection", "()Lcom/thor/app/auto/common/MusicServiceConnection;", "musicServiceConnection$delegate", "Lkotlin/Lazy;", "init", "", "initAdapter", "observeMusicServiceConnection", "onDestroy", "onLoadData", "onStop", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DemoSguFragment extends BaseBindingFragment<FragmentDemoBinding> {
    public static final String BUNDLE_ACTION = "action";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int REQUEST_SETTINGS = 1001;
    private DemoSguRvAdapter mAdapter;
    private final MediaPlayer mediaPlayer = new MediaPlayer();

    /* renamed from: musicServiceConnection$delegate, reason: from kotlin metadata */
    private final Lazy musicServiceConnection = LazyKt.lazy(new Function0<MusicServiceConnection>() { // from class: com.thor.app.gui.fragments.demo.DemoSguFragment$musicServiceConnection$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final MusicServiceConnection invoke() {
            InjectorUtils injectorUtils = InjectorUtils.INSTANCE;
            Context contextRequireContext = this.this$0.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
            return injectorUtils.provideMusicServiceConnection(contextRequireContext);
        }
    });
    private final Function3<LayoutInflater, ViewGroup, Boolean, FragmentDemoBinding> bindingInflater = DemoSguFragment$bindingInflater$1.INSTANCE;

    @JvmStatic
    public static final DemoSguFragment newInstance(String str) {
        return INSTANCE.newInstance(str);
    }

    private final void observeMusicServiceConnection() {
    }

    private final MusicServiceConnection getMusicServiceConnection() {
        return (MusicServiceConnection) this.musicServiceConnection.getValue();
    }

    @Override // com.thor.app.gui.fragments.base.BaseBindingFragment
    public Function3<LayoutInflater, ViewGroup, Boolean, FragmentDemoBinding> getBindingInflater() {
        return this.bindingInflater;
    }

    @Override // com.thor.app.gui.fragments.base.BaseFragment
    public void init() {
        initAdapter();
        observeMusicServiceConnection();
        onLoadData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    private final void initAdapter() {
        this.mAdapter = new DemoSguRvAdapter();
        getBinding().recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView recyclerView = getBinding().recyclerView;
        DemoSguRvAdapter demoSguRvAdapter = this.mAdapter;
        DemoSguRvAdapter demoSguRvAdapter2 = null;
        if (demoSguRvAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            demoSguRvAdapter = null;
        }
        recyclerView.setAdapter(demoSguRvAdapter);
        DemoSguRvAdapter demoSguRvAdapter3 = this.mAdapter;
        if (demoSguRvAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            demoSguRvAdapter3 = null;
        }
        demoSguRvAdapter3.setRecyclerView(getBinding().recyclerView);
        DemoSguRvAdapter demoSguRvAdapter4 = this.mAdapter;
        if (demoSguRvAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            demoSguRvAdapter2 = demoSguRvAdapter4;
        }
        demoSguRvAdapter2.setOnItemClickListener(new RecyclerCollectionAdapter.OnItemClickListener<DemoSguPackage>() { // from class: com.thor.app.gui.fragments.demo.DemoSguFragment$initAdapter$$inlined$addItemClickListener$default$1
            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(DemoSguPackage item, int position) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(DemoSguPackage item, View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(DemoSguPackage item) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                DemoSguPackage demoSguPackage = item;
                try {
                    this.this$0.mediaPlayer.reset();
                    List<String> track = demoSguPackage.getTrack();
                    if (track != null) {
                        Context contextRequireContext = this.this$0.requireContext();
                        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
                        int iCreateRawResIdByName = ContextKt.createRawResIdByName(contextRequireContext, (String) CollectionsKt.random(track, Random.INSTANCE));
                        Context contextRequireContext2 = this.this$0.requireContext();
                        Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext()");
                        this.this$0.mediaPlayer.setDataSource(this.this$0.requireContext(), ContextKt.createUriForResource(contextRequireContext2, iCreateRawResIdByName));
                        this.this$0.mediaPlayer.prepareAsync();
                        this.this$0.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.thor.app.gui.fragments.demo.DemoSguFragment$initAdapter$1$1$1
                            @Override // android.media.MediaPlayer.OnPreparedListener
                            public final void onPrepared(MediaPlayer mediaPlayer) throws IllegalStateException {
                                mediaPlayer.start();
                            }
                        });
                        this.this$0.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.thor.app.gui.fragments.demo.DemoSguFragment$initAdapter$1$1$2
                            @Override // android.media.MediaPlayer.OnCompletionListener
                            public final void onCompletion(MediaPlayer mediaPlayer) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Timber.INSTANCE.e(e);
                }
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() throws IllegalStateException {
        super.onStop();
        this.mediaPlayer.stop();
    }

    private final void onLoadData() {
        BufferedReader bufferedReader;
        AssetManager assets;
        InputStream inputStreamOpen;
        FragmentActivity activity = getActivity();
        DemoSguRvAdapter demoSguRvAdapter = null;
        if (activity == null || (assets = activity.getAssets()) == null || (inputStreamOpen = assets.open(Constants.SGU_DEMO_SOUNDS_JSON)) == null) {
            bufferedReader = null;
        } else {
            Reader inputStreamReader = new InputStreamReader(inputStreamOpen, Charsets.UTF_8);
            bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
        }
        BufferedReader bufferedReader2 = bufferedReader;
        try {
            BufferedReader bufferedReader3 = bufferedReader2;
            String text = bufferedReader3 != null ? TextStreamsKt.readText(bufferedReader3) : null;
            CloseableKt.closeFinally(bufferedReader2, null);
            List list = (List) new Gson().fromJson(text, new TypeToken<List<? extends DemoSguPackage>>() { // from class: com.thor.app.gui.fragments.demo.DemoSguFragment$onLoadData$soundType$1
            }.getType());
            Timber.INSTANCE.i("DemoSounds: %s", list);
            DemoSguRvAdapter demoSguRvAdapter2 = this.mAdapter;
            if (demoSguRvAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            } else {
                demoSguRvAdapter = demoSguRvAdapter2;
            }
            demoSguRvAdapter.clearAndAddAll(list);
        } finally {
        }
    }

    /* compiled from: DemoSguFragment.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/thor/app/gui/fragments/demo/DemoSguFragment$Companion;", "", "()V", "BUNDLE_ACTION", "", "REQUEST_SETTINGS", "", "newInstance", "Lcom/thor/app/gui/fragments/demo/DemoSguFragment;", "action", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ DemoSguFragment newInstance$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.newInstance(str);
        }

        @JvmStatic
        public final DemoSguFragment newInstance(String action) {
            DemoSguFragment demoSguFragment = new DemoSguFragment();
            if (action != null) {
                Bundle bundle = new Bundle();
                bundle.putString("action", action);
                demoSguFragment.setArguments(bundle);
            }
            return demoSguFragment;
        }
    }
}
