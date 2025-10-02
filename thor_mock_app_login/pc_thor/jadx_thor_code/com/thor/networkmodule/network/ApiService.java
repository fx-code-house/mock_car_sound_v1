package com.thor.networkmodule.network;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.thor.networkmodule.model.firmware.FirmwareProfile;
import com.thor.networkmodule.model.firmware.FirmwareProfileList;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.CanConfigurationsResponse;
import com.thor.networkmodule.model.responses.CarInfoAuthorizeResponse;
import com.thor.networkmodule.model.responses.DriveSelectResponse;
import com.thor.networkmodule.model.responses.NotificationsResponse;
import com.thor.networkmodule.model.responses.PasswordValidationResponse;
import com.thor.networkmodule.model.responses.ShopSoundPackagesResponse;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.model.responses.SignUpResponse;
import com.thor.networkmodule.model.responses.car.CarFuelTypeResponse;
import com.thor.networkmodule.model.responses.car.CarGenerationsResponse;
import com.thor.networkmodule.model.responses.car.CarMarksResponse;
import com.thor.networkmodule.model.responses.car.CarModelsResponse;
import com.thor.networkmodule.model.responses.car.CarSeriesResponse;
import com.thor.networkmodule.model.responses.googleauth.SingInFromGoogleResponse;
import com.thor.networkmodule.model.responses.sgu.SguSoundSetDetailsResponse;
import com.thor.networkmodule.model.responses.sgu.SguSoundSetsResponse;
import com.thor.networkmodule.model.responses.sgu.SguSoundsResponse;
import com.thor.networkmodule.model.responses.soundpackage.SoundPackageDescriptionResponse;
import com.thor.networkmodule.model.responses.subscriptions.SubscriptionsResponse;
import io.reactivex.Observable;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/* compiled from: ApiService.kt */
@Metadata(d1 = {"\u0000à\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000 \u0083\u00012\u00020\u0001:\u0002\u0083\u0001J!\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007JC\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\u000f\u001a\u00020\u0006H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0010J\"\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\b\b\u0001\u0010\u0014\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J\u001e\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u00122\b\b\u0001\u0010\u0005\u001a\u00020\u0006H'J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0012H'J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0012H'J\u001e\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u00122\b\b\u0001\u0010\u0005\u001a\u00020\u0006H'J0\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00122\n\b\u0001\u0010\u001e\u001a\u0004\u0018\u00010\u00062\n\b\u0001\u0010\u0014\u001a\u0004\u0018\u00010\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J\u001e\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u00122\b\b\u0001\u0010\u0005\u001a\u00020\u0006H'J:\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u00122\n\b\u0001\u0010\u001e\u001a\u0004\u0018\u00010\u00062\n\b\u0001\u0010\"\u001a\u0004\u0018\u00010\u00062\b\b\u0003\u0010#\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u0012H'J\"\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\b\b\u0001\u0010\u0014\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J@\u0010'\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u00062\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010\u001e\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'Jh\u0010)\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010*\u001a\u00020\u000b2\b\b\u0001\u0010+\u001a\u00020\u000b2\b\b\u0001\u0010\u0015\u001a\u00020\u00062\b\b\u0001\u0010,\u001a\u00020\u000b2\b\b\u0001\u0010-\u001a\u00020\u000b2\b\b\u0001\u0010\u001e\u001a\u00020\u0006H'J@\u0010.\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010/\u001a\u00020\u000b2\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J6\u00100\u001a\b\u0012\u0004\u0012\u0002010\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010\u000f\u001a\u00020\u0006H'Jh\u00102\u001a\b\u0012\u0004\u0012\u0002030\u00122\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010*\u001a\u00020\u000b2\b\b\u0001\u0010+\u001a\u00020\u000b2\b\b\u0001\u0010,\u001a\u00020\u000b2\b\b\u0001\u0010-\u001a\u00020\u000b2\b\b\u0001\u0010\u0015\u001a\u00020\u00062\b\b\u0001\u0010\u001e\u001a\u00020\u0006H'J,\u00104\u001a\b\u0012\u0004\u0012\u0002050\u00122\b\b\u0001\u0010+\u001a\u00020\u000b2\b\b\u0001\u0010-\u001a\u00020\u000b2\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J\u0018\u00106\u001a\b\u0012\u0004\u0012\u0002070\u00122\b\b\u0001\u0010+\u001a\u00020\u000bH'J\u0018\u00108\u001a\b\u0012\u0004\u0012\u0002090\u00122\b\b\u0001\u0010*\u001a\u00020\u000bH'J\u0018\u0010:\u001a\b\u0012\u0004\u0012\u00020;0\u00122\b\b\u0001\u0010,\u001a\u00020\u000bH'J\"\u0010<\u001a\b\u0012\u0004\u0012\u00020\t0\u00122\b\b\u0001\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\u000f\u001a\u00020\u0006H'J6\u0010=\u001a\b\u0012\u0004\u0012\u00020>0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J.\u0010?\u001a\b\u0012\u0004\u0012\u00020@0\u00122\n\b\u0001\u0010\u001e\u001a\u0004\u0018\u00010\u00062\b\b\u0001\u0010A\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J6\u0010B\u001a\b\u0012\u0004\u0012\u00020C0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010\u000f\u001a\u00020\u0006H'J@\u0010D\u001a\b\u0012\u0004\u0012\u00020E0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010F\u001a\u00020\u000b2\b\b\u0001\u0010\u000f\u001a\u00020\u0006H'J@\u0010G\u001a\b\u0012\u0004\u0012\u00020\t0\u00122\b\b\u0001\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\u000f\u001a\u00020\u0006H'J6\u0010H\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00122\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J@\u0010I\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010J\u001a\u00020\u000b2\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J^\u0010K\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010L\u001a\u00020\u00062\b\b\u0001\u0010M\u001a\u00020\u00062\b\b\u0001\u0010F\u001a\u00020\u000b2\b\b\u0001\u0010N\u001a\u00020O2\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J@\u0010P\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010Q\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'JJ\u0010R\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010S\u001a\u00020\u00062\b\b\u0001\u0010Q\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J@\u0010T\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010F\u001a\u00020\u000b2\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J@\u0010U\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010V\u001a\u00020\u000b2\b\b\u0001\u0010W\u001a\u00020\u000b2\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'Jh\u0010X\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010Y\u001a\u00020\u00062\b\b\u0001\u0010Z\u001a\u00020\u000b2\b\b\u0001\u0010V\u001a\u00020\u000b2\b\b\u0001\u0010[\u001a\u00020\u000b2\b\b\u0001\u0010\\\u001a\u00020\u00062\b\b\u0001\u0010]\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J6\u0010^\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010V\u001a\u00020\u000b2\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'JT\u0010_\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010V\u001a\u00020\u000b2\b\b\u0001\u0010[\u001a\u00020\u000b2\b\b\u0001\u0010`\u001a\u00020\u00062\b\b\u0001\u0010a\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J6\u0010b\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010c\u001a\u00020\u00062\b\b\u0001\u0010d\u001a\u00020\u000b2\b\b\u0001\u0010e\u001a\u00020\u0006H'J@\u0010f\u001a\b\u0012\u0004\u0012\u00020g0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u00062\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010\u001e\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J\u0090\u0001\u0010h\u001a\b\u0012\u0004\u0012\u00020g0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u00062\b\b\u0001\u0010i\u001a\u00020\u00062\b\b\u0001\u0010\u001e\u001a\u00020\u00062\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010*\u001a\u00020\u000b2\b\b\u0001\u0010+\u001a\u00020\u000b2\b\b\u0001\u0010,\u001a\u00020\u000b2\b\b\u0001\u0010-\u001a\u00020\u000b2\b\b\u0001\u0010A\u001a\u00020\u00062\b\b\u0001\u0010\"\u001a\u00020\u00062\b\b\u0001\u0010j\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J@\u0010k\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010Y\u001a\u00020\u00062\b\b\u0001\u0010l\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J`\u0010m\u001a\b\u0012\u0004\u0012\u00020g0\u00122\n\b\u0001\u0010\u001e\u001a\u0004\u0018\u00010\u00062\b\b\u0001\u0010j\u001a\u00020\u00062\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010n\u001a\u00020o2\b\b\u0001\u0010\u000f\u001a\u00020\u00062\b\b\u0001\u0010p\u001a\u00020\u00062\b\b\u0001\u0010A\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u000bH'J8\u0010q\u001a\b\u0012\u0004\u0012\u00020g0\u00122\n\b\u0001\u0010\u000e\u001a\u0004\u0018\u00010\u00062\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010i\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J6\u0010r\u001a\b\u0012\u0004\u0012\u00020g0\u00122\b\b\u0001\u0010s\u001a\u00020\u00062\b\b\u0001\u0010\u0014\u001a\u00020\u00062\b\b\u0001\u0010\u000f\u001a\u00020\u00062\b\b\u0001\u0010p\u001a\u00020\u0006H'J8\u0010t\u001a\b\u0012\u0004\u0012\u00020u0\u00122\n\b\u0001\u0010\u000e\u001a\u0004\u0018\u00010\u00062\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010p\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J6\u0010v\u001a\b\u0012\u0004\u0012\u00020w0\u00122\b\b\u0001\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'J^\u0010x\u001a\b\u0012\u0004\u0012\u00020y0\u00122\b\b\u0001\u0010\r\u001a\u00020\u00062\b\b\u0001\u0010*\u001a\u00020\u000b2\b\b\u0001\u0010+\u001a\u00020\u000b2\b\b\u0001\u0010,\u001a\u00020\u000b2\b\b\u0001\u0010-\u001a\u00020\u000b2\b\b\u0001\u0010z\u001a\u00020\u00062\b\b\u0001\u0010\u000f\u001a\u00020\u00062\b\b\u0001\u0010p\u001a\u00020\u0006H'Jh\u0010x\u001a\b\u0012\u0004\u0012\u00020y0\u00122\b\b\u0001\u0010s\u001a\u00020\u00062\b\b\u0001\u0010\u0014\u001a\u00020\u00062\b\b\u0001\u0010*\u001a\u00020\u000b2\b\b\u0001\u0010+\u001a\u00020\u000b2\b\b\u0001\u0010,\u001a\u00020\u000b2\b\b\u0001\u0010-\u001a\u00020\u000b2\b\b\u0001\u0010z\u001a\u00020\u00062\b\b\u0001\u0010\u000f\u001a\u00020\u00062\b\b\u0001\u0010p\u001a\u00020\u0006H'J.\u0010{\u001a\b\u0012\u0004\u0012\u00020(0\u00122\b\b\u0001\u0010Y\u001a\u00020|2\n\b\u0001\u0010}\u001a\u0004\u0018\u00010~2\b\b\u0001\u0010\u0015\u001a\u00020\u0006H'JC\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020(0\u00122\t\b\u0001\u0010\u0080\u0001\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u00062\t\b\u0001\u0010\u0081\u0001\u001a\u00020\u00062\b\b\u0001\u0010n\u001a\u00020\u00062\t\b\u0001\u0010\u0082\u0001\u001a\u00020\u0006H'\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0084\u0001"}, d2 = {"Lcom/thor/networkmodule/network/ApiService;", "", "coroutineGetFile", "Lretrofit2/Response;", "Lokhttp3/ResponseBody;", "fullUrl", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "coroutinePostGetShopSoundPack", "Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageDescriptionResponse;", "packageId", "", "userId", "deviceId", "token", "language", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "rxChangeServerPasswordValidationResponse", "Lio/reactivex/Observable;", "Lcom/thor/networkmodule/model/responses/PasswordValidationResponse;", "password", "languageCode", "rxGetCanFile", "rxGetCarMarks", "Lcom/thor/networkmodule/model/responses/car/CarMarksResponse;", "rxGetDemoShopSoundPackages", "Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;", "rxGetFile", "rxGetFirmwareAllProfiles", "Lcom/thor/networkmodule/model/firmware/FirmwareProfileList;", "deviceSn", "rxGetFirmwareFile", "rxGetFirmwareProfile", "Lcom/thor/networkmodule/model/firmware/FirmwareProfile;", "hardwareVersion", "flag", "rxGetSubscriptions", "Lcom/thor/networkmodule/model/responses/subscriptions/SubscriptionsResponse;", "rxLogsPasswordValidation", "rxPostBlockBoard", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "rxPostChangeCarModels", "carMarkId", "carModelId", "carGenerationId", "carSeriesId", "rxPostDeleteUserNotifications", "notificationId", "rxPostGetAllSguSounds", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundsResponse;", "rxPostGetCanFile", "Lcom/thor/networkmodule/model/responses/CanConfigurationsResponse;", "rxPostGetCarFuelType", "Lcom/thor/networkmodule/model/responses/car/CarFuelTypeResponse;", "rxPostGetCarGenerations", "Lcom/thor/networkmodule/model/responses/car/CarGenerationsResponse;", "rxPostGetCarModels", "Lcom/thor/networkmodule/model/responses/car/CarModelsResponse;", "rxPostGetCarSeries", "Lcom/thor/networkmodule/model/responses/car/CarSeriesResponse;", "rxPostGetDemoShopSoundPack", "rxPostGetDriveSelect", "Lcom/thor/networkmodule/model/responses/DriveSelectResponse;", "rxPostGetInfoFromAuthorize", "Lcom/thor/networkmodule/model/responses/CarInfoAuthorizeResponse;", "deviceBle", "rxPostGetSguSoundPack", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetsResponse;", "rxPostGetSguSoundPackDetails", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetDetailsResponse;", "sguId", "rxPostGetShopSoundPack", "rxPostGetShopSoundPackages", "rxPostGooglePlayBilling", "soundPackId", "rxPostGooglePlayBillingSgu", "paymentToken", "transactionId", "amount", "", "rxPostNotificationRemove", "device", "rxPostNotificationToken", "fcmToken", "rxPostPurchaseSgu", "rxPostSendStatisticsAboutActionWithSoundPackage", "soundPackageId", "action", "rxPostSendStatisticsAboutSoundPackage", "serialNumber", "firmwareVersion", "presetType", "realSerialNumber", "hardware_version", "rxPostSendStatisticsLoadFailed", "rxPostSendStatisticsParameterChange", "parameterInHex", "value", "rxPostSubscriptionActivate", "iap", "subscriptionId", "receipt", "rxPostToolsCarInfo", "Lcom/thor/networkmodule/model/responses/SignInResponse;", "rxPostToolsConnect", "platform", "appVersion", "rxPostUniversalDataStatistics", "pack", "rxPostUserAuthorize", "firmware", "", "appType", "rxPostUserAuthorizeGoogle", "rxPostUserEmailAuthorize", "email", "rxPostUserGoogleAuthorize", "Lcom/thor/networkmodule/model/responses/googleauth/SingInFromGoogleResponse;", "rxPostUserNotifications", "Lcom/thor/networkmodule/model/responses/NotificationsResponse;", "rxPostUserRegister", "Lcom/thor/networkmodule/model/responses/SignUpResponse;", "deviceSN", "rxSendLogs", "Lokhttp3/RequestBody;", "logFile", "Lokhttp3/MultipartBody$Part;", "rxUpdateVersion", "apiKey", "sn", "hardware", "Companion", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public interface ApiService {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;
    public static final String HTTP_400_ERROR = "HTTP 400";
    public static final String HTTP_500_ERROR = "HTTP 500";

    @GET
    Object coroutineGetFile(@Url String str, Continuation<? super Response<ResponseBody>> continuation);

    @FormUrlEncoded
    @POST("/api/sounds/get-shop-sound-pack")
    Object coroutinePostGetShopSoundPack(@Field("id_sound_pkg") int i, @Field("user_id") int i2, @Field("device_id") String str, @Field("apikey") String str2, @Field("language") String str3, Continuation<? super SoundPackageDescriptionResponse> continuation);

    @FormUrlEncoded
    @POST("/api/password-utility/change-server")
    Observable<PasswordValidationResponse> rxChangeServerPasswordValidationResponse(@Field("password") String password, @Field("language") String languageCode);

    @GET
    Observable<Response<ResponseBody>> rxGetCanFile(@Url String fullUrl);

    @GET("/api/cars/get-mark-list")
    Observable<CarMarksResponse> rxGetCarMarks();

    @GET("/api/shop/get-demo-sound-packages")
    Observable<ShopSoundPackagesResponse> rxGetDemoShopSoundPackages();

    @GET
    Observable<Response<ResponseBody>> rxGetFile(@Url String fullUrl);

    @FormUrlEncoded
    @POST("/api/firmware/all")
    Observable<FirmwareProfileList> rxGetFirmwareAllProfiles(@Field("deviceSn") String deviceSn, @Field("password") String password, @Field("language") String languageCode);

    @GET
    Observable<Response<ResponseBody>> rxGetFirmwareFile(@Url String fullUrl);

    @FormUrlEncoded
    @POST("/api/firmware")
    Observable<FirmwareProfile> rxGetFirmwareProfile(@Field("deviceSn") String deviceSn, @Field("hardware_version") String hardwareVersion, @Field("flag") String flag, @Field("language") String languageCode);

    @FormUrlEncoded
    @GET("/api/v2/subscription")
    Observable<SubscriptionsResponse> rxGetSubscriptions();

    @FormUrlEncoded
    @POST("/api/password-utility/logs")
    Observable<PasswordValidationResponse> rxLogsPasswordValidation(@Field("password") String password, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("api/tools/blocked")
    Observable<BaseResponse> rxPostBlockBoard(@Field("apikey") String token, @Field("user_id") String userId, @Field("deviceId") String deviceId, @Field("device_sn") String deviceSn, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/cars/change-car")
    Observable<BaseResponse> rxPostChangeCarModels(@Field("apikey") String token, @Field("user_id") int userId, @Field("device_id") String deviceId, @Field("id_car_mark") int carMarkId, @Field("id_car_model") int carModelId, @Field("language") String languageCode, @Field("id_car_generation") int carGenerationId, @Field("id_car_serie") int carSeriesId, @Field("device_sn") String deviceSn);

    @FormUrlEncoded
    @POST("/api/events/delete")
    Observable<BaseResponse> rxPostDeleteUserNotifications(@Field("apikey") String token, @Field("user_id") int userId, @Field("device_id") String deviceId, @Field("notification_id") int notificationId, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/sound-sets/get-all-sounds-sgu")
    Observable<SguSoundsResponse> rxPostGetAllSguSounds(@Field("apikey") String token, @Field("user_id") int userId, @Field("device_id") String deviceId, @Field("language") String language);

    @FormUrlEncoded
    @POST("/api/cars/get-can-file")
    Observable<CanConfigurationsResponse> rxPostGetCanFile(@Field("user_id") int userId, @Field("device_id") String deviceId, @Field("apikey") String token, @Field("id_car_mark") int carMarkId, @Field("id_car_model") int carModelId, @Field("id_car_generation") int carGenerationId, @Field("id_car_serie") int carSeriesId, @Field("language") String languageCode, @Field("device_sn") String deviceSn);

    @FormUrlEncoded
    @POST("/api/cars/get-fuel")
    Observable<CarFuelTypeResponse> rxPostGetCarFuelType(@Field("id_car_model") int carModelId, @Field("id_car_serie") int carSeriesId, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/cars/get-generation-list")
    Observable<CarGenerationsResponse> rxPostGetCarGenerations(@Field("id_car_model") int carModelId);

    @FormUrlEncoded
    @POST("/api/cars/get-model-list")
    Observable<CarModelsResponse> rxPostGetCarModels(@Field("id_car_mark") int carMarkId);

    @FormUrlEncoded
    @POST("/api/cars/get-serie-list")
    Observable<CarSeriesResponse> rxPostGetCarSeries(@Field("id_car_generation") int carGenerationId);

    @FormUrlEncoded
    @POST("/api/shop/get-demo-sound-pack")
    Observable<SoundPackageDescriptionResponse> rxPostGetDemoShopSoundPack(@Field("id_sound_pkg") int packageId, @Field("language") String language);

    @FormUrlEncoded
    @POST("/api/cars/get-drive-select")
    Observable<DriveSelectResponse> rxPostGetDriveSelect(@Field("apikey") String token, @Field("user_id") int userId, @Field("device_id") String deviceId, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/v2/user/authorize")
    Observable<CarInfoAuthorizeResponse> rxPostGetInfoFromAuthorize(@Field("device_sn") String deviceSn, @Field("device_ble") String deviceBle, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/sound-sets/get-shop-all-categories")
    Observable<SguSoundSetsResponse> rxPostGetSguSoundPack(@Field("apikey") String token, @Field("user_id") int userId, @Field("device_id") String deviceId, @Field("language") String language);

    @FormUrlEncoded
    @POST("/api/sound-sets/get-shop-sound-set")
    Observable<SguSoundSetDetailsResponse> rxPostGetSguSoundPackDetails(@Field("apikey") String token, @Field("user_id") int userId, @Field("device_id") String deviceId, @Field("id_sound_sets") int sguId, @Field("language") String language);

    @FormUrlEncoded
    @POST("/api/sounds/get-shop-sound-pack")
    Observable<SoundPackageDescriptionResponse> rxPostGetShopSoundPack(@Field("id_sound_pkg") int packageId, @Field("user_id") int userId, @Field("device_id") String deviceId, @Field("apikey") String token, @Field("language") String language);

    @FormUrlEncoded
    @POST("/api/sounds/get-shop-sound-packages")
    Observable<ShopSoundPackagesResponse> rxPostGetShopSoundPackages(@Field("user_id") int userId, @Field("device_id") String deviceId, @Field("apikey") String token, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/pay/sound-pkg")
    Observable<BaseResponse> rxPostGooglePlayBilling(@Field("apikey") String token, @Field("user_id") int userId, @Field("device_id") String deviceId, @Field("id_sound_pkg") int soundPackId, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/pay-set/by-google-pay")
    Observable<BaseResponse> rxPostGooglePlayBillingSgu(@Field("apikey") String token, @Field("user_id") int userId, @Field("device_id") String deviceId, @Field("token") String paymentToken, @Field("google_transaction_id ") String transactionId, @Field("id_sound_sets") int sguId, @Field("amount") float amount, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/notification/remove")
    Observable<BaseResponse> rxPostNotificationRemove(@Field("apikey") String token, @Field("user_id") int userId, @Field("device_id") String deviceId, @Field("device") String device, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/notification/token")
    Observable<BaseResponse> rxPostNotificationToken(@Field("apikey") String token, @Field("user_id") int userId, @Field("device_id") String deviceId, @Field("fcm_token") String fcmToken, @Field("device") String device, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/pay-set/sound-set")
    Observable<BaseResponse> rxPostPurchaseSgu(@Field("apikey") String token, @Field("user_id") int userId, @Field("device_id") String deviceId, @Field("id_sound_sets") int sguId, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/statistics/sound")
    Observable<BaseResponse> rxPostSendStatisticsAboutActionWithSoundPackage(@Field("user_id") int userId, @Field("device_id") String deviceId, @Field("sound_pkg_id") int soundPackageId, @Field("action") int action, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/statistics/sound-pack")
    Observable<BaseResponse> rxPostSendStatisticsAboutSoundPackage(@Field("user_id") int userId, @Field("device_id") String deviceId, @Field("serial_number") String serialNumber, @Field("firmware_version") int firmwareVersion, @Field("sound_pkg_id") int soundPackageId, @Field("preset_type") int presetType, @Field("real_serial_number") String realSerialNumber, @Field("hardware_version") String hardware_version, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/statistics/load-failed")
    Observable<BaseResponse> rxPostSendStatisticsLoadFailed(@Field("user_id") int userId, @Field("device_id") String deviceId, @Field("sound_pkg_id") int soundPackageId, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST(" /api/statistics/parameter-change")
    Observable<BaseResponse> rxPostSendStatisticsParameterChange(@Field("user_id") int userId, @Field("device_id") String deviceId, @Field("sound_pkg_id") int soundPackageId, @Field("preset_type") int presetType, @Field("parameter") String parameterInHex, @Field("value") String value, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/v2/subscription/activate")
    Observable<BaseResponse> rxPostSubscriptionActivate(@Field("apikey") String token, @Field("iap") String iap, @Field("subscriptionId") int subscriptionId, @Field("receipt") String receipt);

    @FormUrlEncoded
    @POST("api/tools/car-info")
    Observable<SignInResponse> rxPostToolsCarInfo(@Field("apikey") String token, @Field("user_id") String userId, @Field("device_id") String deviceId, @Field("device_sn") String deviceSn, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("api/tools/connect")
    Observable<SignInResponse> rxPostToolsConnect(@Field("apikey") String token, @Field("user_id") String userId, @Field("platform") String platform, @Field("device_sn") String deviceSn, @Field("device_id") String deviceId, @Field("id_car_mark") int carMarkId, @Field("id_car_model") int carModelId, @Field("id_car_generation") int carGenerationId, @Field("id_car_serie") int carSeriesId, @Field("device_ble") String deviceBle, @Field("firmware") String hardwareVersion, @Field("app_version") String appVersion, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/statistics/add-by-pack")
    Observable<BaseResponse> rxPostUniversalDataStatistics(@Field("user_id") int userId, @Field("device_id") String deviceId, @Field("sn") String serialNumber, @Field("pack") String pack, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/v2/user/authorize")
    Observable<SignInResponse> rxPostUserAuthorize(@Field("device_sn") String deviceSn, @Field("app_version") String appVersion, @Field("device_id") String deviceId, @Field("firmware") short firmware, @Field("language") String language, @Field("os") String appType, @Field("device_ble") String deviceBle, @Field("id_user") int userId);

    @FormUrlEncoded
    @POST("/api/v2/auth/google")
    Observable<SignInResponse> rxPostUserAuthorizeGoogle(@Field("token") String token, @Field("device_id") String deviceId, @Field("platform") String platform, @Field("language") String languageCode);

    @Deprecated(message = "")
    @FormUrlEncoded
    @POST("/api/user/authorize")
    Observable<SignInResponse> rxPostUserEmailAuthorize(@Field("email") String email, @Field("password") String password, @Field("language") String language, @Field("app_type") String appType);

    @FormUrlEncoded
    @POST("/api/v2/reg/google")
    Observable<SingInFromGoogleResponse> rxPostUserGoogleAuthorize(@Field("token") String token, @Field("device_id") String deviceId, @Field("platform") String appType, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/events")
    Observable<NotificationsResponse> rxPostUserNotifications(@Field("apikey") String token, @Field("user_id") int userId, @Field("device_id") String deviceId, @Field("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/v2/user/register")
    Observable<SignUpResponse> rxPostUserRegister(@Field("device_id") String deviceId, @Field("id_car_mark") int carMarkId, @Field("id_car_model") int carModelId, @Field("id_car_generation") int carGenerationId, @Field("id_car_serie") int carSeriesId, @Field("device_sn") String deviceSN, @Field("language") String language, @Field("os") String appType);

    @Deprecated(message = "")
    @FormUrlEncoded
    @POST("/api/user/register")
    Observable<SignUpResponse> rxPostUserRegister(@Field("email") String email, @Field("password") String password, @Field("id_car_mark") int carMarkId, @Field("id_car_model") int carModelId, @Field("id_car_generation") int carGenerationId, @Field("id_car_serie") int carSeriesId, @Field("device_sn") String deviceSN, @Field("language") String language, @Field("app_type") String appType);

    @POST("/api/v2/app-logs/upload")
    @Multipart
    Observable<BaseResponse> rxSendLogs(@Part("user_device_sn") RequestBody serialNumber, @Part MultipartBody.Part logFile, @Part("language") String languageCode);

    @FormUrlEncoded
    @POST("/api/tools/update-versions")
    Observable<BaseResponse> rxUpdateVersion(@Field("apikey") String apiKey, @Field("user_id") String userId, @Field("sn") String sn, @Field("firmware") String firmware, @Field("hardware") String hardware);

    /* compiled from: ApiService.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lcom/thor/networkmodule/network/ApiService$Companion;", "", "()V", "HTTP_400_ERROR", "", "HTTP_500_ERROR", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String HTTP_400_ERROR = "HTTP 400";
        public static final String HTTP_500_ERROR = "HTTP 500";

        private Companion() {
        }
    }

    /* compiled from: ApiService.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        public static /* synthetic */ Observable rxGetFirmwareProfile$default(ApiService apiService, String str, String str2, String str3, String str4, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: rxGetFirmwareProfile");
            }
            if ((i & 4) != 0) {
                str3 = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
            }
            return apiService.rxGetFirmwareProfile(str, str2, str3, str4);
        }
    }
}
