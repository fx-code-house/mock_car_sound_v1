package com.google.firebase.messaging;

import android.content.Intent;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.encoders.EncodingException;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import com.google.firebase.messaging.Constants;
import java.io.IOException;

/* compiled from: com.google.firebase:firebase-messaging@@21.0.0 */
/* loaded from: classes2.dex */
final class FirelogAnalyticsEvent {
    private final String eventType;
    private final Intent intent;

    /* compiled from: com.google.firebase:firebase-messaging@@21.0.0 */
    static class FirelogAnalyticsEventEncoder implements ObjectEncoder<FirelogAnalyticsEvent> {
        FirelogAnalyticsEventEncoder() {
        }

        @Override // com.google.firebase.encoders.Encoder
        public void encode(FirelogAnalyticsEvent firelogAnalyticsEvent, ObjectEncoderContext objectEncoderContext) throws EncodingException, IOException {
            Intent intent = firelogAnalyticsEvent.getIntent();
            objectEncoderContext.add(Constants.FirelogAnalytics.PARAM_TTL, MessagingAnalytics.getTtl(intent));
            objectEncoderContext.add("event", firelogAnalyticsEvent.getEventType());
            objectEncoderContext.add(Constants.FirelogAnalytics.PARAM_INSTANCE_ID, MessagingAnalytics.getInstanceId());
            objectEncoderContext.add(Constants.FirelogAnalytics.PARAM_PRIORITY, MessagingAnalytics.getPriority(intent));
            objectEncoderContext.add(Constants.FirelogAnalytics.PARAM_PACKAGE_NAME, MessagingAnalytics.getPackageName());
            objectEncoderContext.add(Constants.FirelogAnalytics.PARAM_SDK_PLATFORM, Constants.FirelogAnalytics.SDK_PLATFORM_ANDROID);
            objectEncoderContext.add(Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE, MessagingAnalytics.getMessageTypeForFirelog(intent));
            String messageId = MessagingAnalytics.getMessageId(intent);
            if (messageId != null) {
                objectEncoderContext.add(Constants.FirelogAnalytics.PARAM_MESSAGE_ID, messageId);
            }
            String topic = MessagingAnalytics.getTopic(intent);
            if (topic != null) {
                objectEncoderContext.add(Constants.FirelogAnalytics.PARAM_TOPIC, topic);
            }
            String collapseKey = MessagingAnalytics.getCollapseKey(intent);
            if (collapseKey != null) {
                objectEncoderContext.add(Constants.FirelogAnalytics.PARAM_COLLAPSE_KEY, collapseKey);
            }
            if (MessagingAnalytics.getMessageLabel(intent) != null) {
                objectEncoderContext.add(Constants.FirelogAnalytics.PARAM_ANALYTICS_LABEL, MessagingAnalytics.getMessageLabel(intent));
            }
            if (MessagingAnalytics.getComposerLabel(intent) != null) {
                objectEncoderContext.add(Constants.FirelogAnalytics.PARAM_COMPOSER_LABEL, MessagingAnalytics.getComposerLabel(intent));
            }
            String projectNumber = MessagingAnalytics.getProjectNumber();
            if (projectNumber != null) {
                objectEncoderContext.add(Constants.FirelogAnalytics.PARAM_PROJECT_NUMBER, projectNumber);
            }
        }
    }

    FirelogAnalyticsEvent(String str, Intent intent) {
        this.eventType = Preconditions.checkNotEmpty(str, "evenType must be non-null");
        this.intent = (Intent) Preconditions.checkNotNull(intent, "intent must be non-null");
    }

    /* compiled from: com.google.firebase:firebase-messaging@@21.0.0 */
    static final class FirelogAnalyticsEventWrapperEncoder implements ObjectEncoder<FirelogAnalyticsEventWrapper> {
        FirelogAnalyticsEventWrapperEncoder() {
        }

        @Override // com.google.firebase.encoders.Encoder
        public final void encode(FirelogAnalyticsEventWrapper firelogAnalyticsEventWrapper, ObjectEncoderContext objectEncoderContext) throws EncodingException, IOException {
            objectEncoderContext.add("messaging_client_event", firelogAnalyticsEventWrapper.getFirelogAnalyticsEvent());
        }
    }

    /* compiled from: com.google.firebase:firebase-messaging@@21.0.0 */
    static final class FirelogAnalyticsEventWrapper {
        private final FirelogAnalyticsEvent firelogAnalyticsEvent;

        FirelogAnalyticsEventWrapper(FirelogAnalyticsEvent firelogAnalyticsEvent) {
            this.firelogAnalyticsEvent = (FirelogAnalyticsEvent) Preconditions.checkNotNull(firelogAnalyticsEvent);
        }

        final FirelogAnalyticsEvent getFirelogAnalyticsEvent() {
            return this.firelogAnalyticsEvent;
        }
    }

    final Intent getIntent() {
        return this.intent;
    }

    final String getEventType() {
        return this.eventType;
    }
}
