/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package cn.xxx.winkawaks.puzzle.module.utils;

import android.content.Context;
import android.content.Intent;
import cn.xxx.winkawaks.puzzle.R;

public class LaunchEmailUtil {

  public static void launchEmailToIntent(Context context) {
    Intent msg = new Intent(Intent.ACTION_SEND);

    StringBuilder body = new StringBuilder("\n\n----------\n");
    body.append(EnvironmentInfoUtil.getApplicationInfo(context));

    msg.putExtra(Intent.EXTRA_EMAIL,
        context.getString(R.string.mail_support_feedback_to)
            .split(", "));
    msg.putExtra(Intent.EXTRA_SUBJECT,
        context.getString(R.string.mail_support_feedback_subject));
    msg.putExtra(Intent.EXTRA_TEXT, body.toString());

    msg.setType("message/rfc822");
    context.startActivity(Intent.createChooser(msg,
        context.getString(R.string.email)));
  }
}
