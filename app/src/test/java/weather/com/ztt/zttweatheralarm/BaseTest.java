package weather.com.ztt.zttweatheralarm;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowApplication;

/**
 * Created by vtcmer on 01/01/2018.
 */

public abstract class BaseTest {

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);

        if (RuntimeEnvironment.application != null){
            ShadowApplication shadowApplication = Shadows.shadowOf(RuntimeEnvironment.application);
            shadowApplication.grantPermissions("android.permission.INTERNET");
        }

    }
}
