/*eNote SMS Alerts
'HP : N-IM17207085 (Priority 1) - Dispatched' : 'N-INCSSP-GDO-GPS'
'OS DO TOOLS : N-IM17207085 (Priority 2) - TTO 50 Percent' : 'N-INCSSP-GDO-GPS'
'CVTC : N-IM17207085 (Priority 3) - TTO 75 Percent' : 'L-INCSSP-GDO-GPS'
'KPMI : N-IM17207085 (Priority 3) - TTIR 75 Percent' : 'N-INCSSP-GDO-GPS'
'KPMI : N-IM17207085 (Priority 4) - TTO deadline' : 'N-INCSSP-GDO-GPS'
'BAKM : N-IM17207085 (Priority 4) - TTIR deadline' : 'N-INCSSP-GDO-GPS'
eNote PROD-AMS Action Required:
*/

package com.hpe.rumarco.enotepager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class MainAllNotificationActivity extends SingleFragmentAllNotificationActivity
{
    //Toolbar toolbar;
    @Override
    protected Fragment createFragment()
    {
        return new NotificationAllListFragment();
    }
}
