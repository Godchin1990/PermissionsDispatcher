package permissions.dispatcher.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import permissions.dispatcher.*;
import permissions.dispatcher.sample.camera.CameraPreviewFragment;
import permissions.dispatcher.sample.contacts.ContactsFragment;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button cameraButton = (Button) findViewById(R.id.button_camera);
        cameraButton.setOnClickListener(this);
        Button contactsButton = (Button) findViewById(R.id.button_contacts);
        contactsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_camera:
                // NOTE: delegate the permission handling to generated method
                MainActivityPermissionsDispatcher.showCameraWithCheck(this);
                break;
            case R.id.button_contacts:
                // NOTE: delegate the permission handling to generated method
                MainActivityPermissionsDispatcher.showContactsWithCheck(this);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.
                onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void showCamera() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sample_content_fragment, CameraPreviewFragment.newInstance())
                .addToBackStack("camera")
                .commitAllowingStateLoss();
    }

    @NeedsPermissions({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void showContacts() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sample_content_fragment, ContactsFragment.newInstance())
                .addToBackStack("contacts")
                .commitAllowingStateLoss();
    }

    @ShowsRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera() {
        Toast.makeText(this, R.string.permission_camera_rationale, Toast.LENGTH_SHORT).show();
    }

    @ShowsRationales({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void showRationaleForContacts() {
        Toast.makeText(this, R.string.permission_contacts_rationale, Toast.LENGTH_SHORT).show();
    }

    @DeniedPermission(Manifest.permission.CAMERA)
    void deniedActionForCamera() {
        Toast.makeText(this, R.string.permission_camera_denied, Toast.LENGTH_SHORT).show();
    }

    @DeniedPermissions({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void deniedActionForContacts() {
        Toast.makeText(this, R.string.permission_contacts_denied, Toast.LENGTH_SHORT).show();
    }

    public void onBackClick(View view) {
        getSupportFragmentManager().popBackStack();
    }
}
