package com.thor.app.gui.activities.preset.sgu.items;

import android.view.View;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ItemSguSoundFavBinding;
import com.thor.app.gui.views.sgu.OnSguSoundClickListener;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.xwray.groupie.viewbinding.BindableItem;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguSoundFavItem.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B5\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\tJ\u0018\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0012H\u0016J\u0018\u0010\u0016\u001a\n \u0017*\u0004\u0018\u00010\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0014R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u001a"}, d2 = {"Lcom/thor/app/gui/activities/preset/sgu/items/SguSoundFavItem;", "Lcom/xwray/groupie/viewbinding/BindableItem;", "Lcom/carsystems/thor/app/databinding/ItemSguSoundFavBinding;", "model", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "onFav", "Lkotlin/Function1;", "", "onPlay", "(Lcom/thor/networkmodule/model/responses/sgu/SguSound;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getModel", "()Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "getOnFav", "()Lkotlin/jvm/functions/Function1;", "getOnPlay", "bind", "binding", "position", "", "getId", "", "getLayout", "initializeViewBinding", "kotlin.jvm.PlatformType", "view", "Landroid/view/View;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguSoundFavItem extends BindableItem<ItemSguSoundFavBinding> {
    private final SguSound model;
    private final Function1<SguSound, Unit> onFav;
    private final Function1<SguSound, Unit> onPlay;

    @Override // com.xwray.groupie.Item
    public int getLayout() {
        return R.layout.item_sgu_sound_fav;
    }

    public final SguSound getModel() {
        return this.model;
    }

    public final Function1<SguSound, Unit> getOnFav() {
        return this.onFav;
    }

    public final Function1<SguSound, Unit> getOnPlay() {
        return this.onPlay;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SguSoundFavItem(SguSound model, Function1<? super SguSound, Unit> onFav, Function1<? super SguSound, Unit> onPlay) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(onFav, "onFav");
        Intrinsics.checkNotNullParameter(onPlay, "onPlay");
        this.model = model;
        this.onFav = onFav;
        this.onPlay = onPlay;
    }

    @Override // com.xwray.groupie.viewbinding.BindableItem
    public void bind(ItemSguSoundFavBinding binding, int position) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        binding.viewSoundPreset.setSoundPackage(this.model);
        binding.viewSoundPreset.setOnClickListener(new OnSguSoundClickListener() { // from class: com.thor.app.gui.activities.preset.sgu.items.SguSoundFavItem.bind.1
            @Override // com.thor.app.gui.views.sgu.OnSguSoundClickListener
            public void onConfigClick(SguSound sound) {
                Intrinsics.checkNotNullParameter(sound, "sound");
            }

            @Override // com.thor.app.gui.views.sgu.OnSguSoundClickListener
            public void onFavClick(SguSound sound) {
                Intrinsics.checkNotNullParameter(sound, "sound");
                SguSoundFavItem.this.getModel().setFavourite(!SguSoundFavItem.this.getModel().isFavourite());
                SguSoundFavItem.this.notifyChanged();
                SguSoundFavItem.this.getOnFav().invoke(SguSoundFavItem.this.getModel());
            }

            @Override // com.thor.app.gui.views.sgu.OnSguSoundClickListener
            public void onPlayClick(SguSound sound) {
                Intrinsics.checkNotNullParameter(sound, "sound");
                SguSoundFavItem.this.getOnPlay().invoke(sound);
            }
        });
    }

    @Override // com.xwray.groupie.Item
    public long getId() {
        return this.model.getId();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xwray.groupie.viewbinding.BindableItem
    public ItemSguSoundFavBinding initializeViewBinding(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        return ItemSguSoundFavBinding.bind(view);
    }
}
