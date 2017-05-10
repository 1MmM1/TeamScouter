package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

import java.io.File;
import java.util.UUID;

public class TeamFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {

    private static final String TAG = "TeamFragment";

    private static final String ARG_TEAM_ID = "team_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_DELETE = "DialogDelete";
    private static final String DIALOG_PICTURE = "DialogPicture";
    private static final String DIALOG_HANG = "DialogHang";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_DELETE = 100;
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_PHOTO = 2;
    private static final int REQUEST_ZOOM = 200;
    private static final int REQUEST_HANG = 300;

    private Team mTeam;
    private File mPhotoFile;
    private EditText mTitleField;
    private EditText mNumberField;
    private Button mDateButton;
    private CheckBox mCubesCheckbox;
    private TextView mDisquals;
    private Button mReportButton;
    private Button mContactButton;
    private Button mCallButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private Point mPhotoViewSize;
    private Callbacks mCallbacks;
    private Date mDate;
    private EditText mWins;
    private EditText mLosses;
    private EditText mTies;
    private Spinner mTypeSpinner;
    private Button mHangButton;
    private ImageButton mPlusButton;
    private ImageButton mSubtractButton;
//    private SeekBar mHanging;

    private int teamId;

    public interface Callbacks
    {
        void onTeamUpdated(Team team);
    }

    public static TeamFragment newInstance(UUID teamId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TEAM_ID, teamId);

        TeamFragment fragment = new TeamFragment();
        fragment.setArguments(args);
        return(fragment);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID teamId = (UUID) getArguments().getSerializable(ARG_TEAM_ID);
        mTeam = TeamLab.get(getActivity()).getTeam(teamId);
        mPhotoFile = TeamLab.get(getActivity()).getPhotoFile(mTeam);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        TeamLab.get(getActivity()).updateTeam(mTeam);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_team, container, false);

        mTitleField = (EditText) v.findViewById(R.id.team_name);
        mTitleField.setText(mTeam.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTeam.setTitle(s.toString());
                updateTeam();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mNumberField = (EditText) v.findViewById(R.id.team_number);
        mNumberField.setText(mTeam.getNumber());
        mNumberField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTeam.setNumber(s.toString());
                updateTeam();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.last_played_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mTeam.getDate());
                dialog.setTargetFragment(TeamFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
                updateDate();            }
        });


        mCubesCheckbox = (CheckBox) v.findViewById(R.id.cubes);
        mCubesCheckbox.setChecked(mTeam.isSolved());
        mCubesCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTeam.setSolved(isChecked);
                updateTeam();
            }
        });

        mReportButton = (Button) v.findViewById(R.id.team_summary);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent i = ShareCompat.IntentBuilder.from(getActivity()).setType("text/plain").setText(getTeamSummary())
                        .setSubject(getString(R.string.team_report_subject)).getIntent();
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });

        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mContactButton = (Button) v.findViewById(R.id.team_contact);
        mContactButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        if(mTeam.getSuspect() != null)
        {
            mContactButton.setText(mTeam.getSuspect());
        }

        mCallButton = (Button) v.findViewById(R.id.call_contact);
        mCallButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String[] fields = new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER};
                String whereClause = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
                String[] args = new String[] {Integer.toString(teamId)};
                Cursor c = getActivity().getContentResolver().query(phoneUri, fields, whereClause, args, null);

                try
                {
                    if(c.getCount() == 0)
                    {
                        return;
                    }
                    c.moveToFirst();
                    String suspectNumber = c.getString(0);
                    Uri number = Uri.parse("tel:" + suspectNumber);
                    Intent i = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(i);
                }
                finally
                {
                    c.close();
                }
            }
        });

        PackageManager packageManager = getActivity().getPackageManager();
        if(packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null)
        {
            mContactButton.setEnabled(false);
        }
        if(mTeam.getSuspect() == null)
        {
            mCallButton.setEnabled(false);
        }

        mPhotoButton = (ImageButton) v.findViewById(R.id.team_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = (mPhotoFile != null) && (captureImage.resolveActivity(packageManager) != null);
        mPhotoButton.setEnabled(canTakePhoto);

        if(canTakePhoto)
        {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.team_photo);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentManager manager = getFragmentManager();
                PictureFragment dialog = PictureFragment.newInstance(mPhotoFile);
                dialog.setTargetFragment(TeamFragment.this, REQUEST_ZOOM);
                dialog.show(manager, DIALOG_PICTURE);
            }
        });

        final ViewTreeObserver observer = mPhotoView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(mPhotoViewSize == null)
                {
                    mPhotoViewSize = new Point(mPhotoView.getWidth(), mPhotoView.getHeight());
                }
                updatePhotoView();
                mPhotoView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        mPlusButton = (ImageButton) v.findViewById(R.id.add_disquals_button);
        mPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mTeam.setDisquals(mTeam.getDisquals() + 1);
                if (mTeam.getDisquals() > 0)
                {
                    mSubtractButton.setEnabled(true);
                }
                updateDisquals();
                updateTeam();
                Log.i(TAG, "Added one: " + mTeam.getDisquals());
            }
        });

        mDisquals = (TextView) v.findViewById(R.id.total_disquals_text_view);
        Log.i(TAG, "Firt Pass: " + mTeam.getDisquals());
        updateDisquals();

        mSubtractButton = (ImageButton) v.findViewById(R.id.subtract_disquals_button);
        mSubtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mTeam.setDisquals(mTeam.getDisquals() - 1);

                if(mTeam.getDisquals() <= 0)
                {
                    mTeam.setDisquals(0);
                    mSubtractButton.setEnabled(false);
                }
                updateDisquals();
                updateTeam();
                Log.i(TAG, "Subtracted one: " + mTeam.getDisquals());
            }
        });


        mWins = (EditText) v.findViewById(R.id.wins);
        mWins.setText(mTeam.getWins());
        mWins.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTeam.setWins(s.toString());
                updateTeam();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLosses = (EditText) v.findViewById(R.id.losses);
        mLosses.setText(mTeam.getLosses());
        mLosses.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTeam.setLosses(s.toString());
                updateTeam();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTies = (EditText) v.findViewById(R.id.ties);
        mTies.setText(mTeam.getTies());
        mTies.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTeam.setTies(s.toString());
                updateTeam();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTypeSpinner = (Spinner) v.findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.types_of_robot, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mTypeSpinner.setAdapter(adapter);
                mTypeSpinner.setOnItemSelectedListener(this);
                mTypeSpinner.setSelection(mTeam.getType());
        Log.i(TAG, "spinner change:" + mTypeSpinner.getSelectedItem().toString());

        mHangButton = (Button) v.findViewById(R.id.hang_button);
        updateHanging();
        mHangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentManager manager = getFragmentManager();
                HangFragment dialog = HangFragment.newInstance(mTeam.getHang());
                dialog.setTargetFragment(TeamFragment.this, REQUEST_HANG);
                dialog.show(manager, DIALOG_HANG);
                updateHanging();
            }
        });

        return v;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if(resultCode != Activity.RESULT_OK || resultCode == REQUEST_ZOOM)
        {
            return;
        }

        if(requestCode == REQUEST_DATE)
        {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTeam.setDate(date);
            updateTeam();
            updateDate();
        }
        else if(requestCode == REQUEST_CONTACT && data != null)
        {
            Uri contactUri = data.getData();
            String[] queryFields = new String[] {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts._ID};
            Cursor c = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);

            try
            {
                if(c.getCount() == 0)
                {
                    return;
                }
                c.moveToFirst();
                String suspect = c.getString(0);
                mTeam.setSuspect(suspect);
                updateTeam();
                mContactButton.setText(suspect);
                mCallButton.setEnabled(true);
                teamId = c.getInt(1);
            }
            finally
            {
                c.close();
            }
        }
        else if(requestCode == REQUEST_PHOTO)
        {
            updateTeam();
            updatePhotoView();
        }

        if(requestCode == REQUEST_DELETE)
        {
            //should this be changed as well, seeing as we can no longer exit the activity?
            TeamLab.get(getActivity()).deleteTeam(mTeam);
            getActivity().finish();
        }

        if(requestCode == REQUEST_HANG)
        {
            mTeam.setHang(data.getIntExtra(HangFragment.EXTRA_POSITION, 0));
            updateTeam();
            updateHanging();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        mTeam.setType(position);
        updateTeam();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_team, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case(R.id.menu_item_delete_team):
                FragmentManager manager = getFragmentManager();
                DeleteConfirmationFragment dialog = DeleteConfirmationFragment.newInstance();
                dialog.setTargetFragment(TeamFragment.this, REQUEST_DELETE);
                dialog.show(manager, DIALOG_DELETE);
                return(true);
            default:
                return(super.onOptionsItemSelected(item));
        }
    }

    private void updateTeam()
    {
        TeamLab.get(getActivity()).updateTeam(mTeam);
        mCallbacks.onTeamUpdated(mTeam);
    }

    private void updateDate() {
        mDateButton.setText(DateFormat.format("EEEE, MMMM d, yyyy", mTeam.getDate()));
    }

    private void updateDisquals()
    {
        mDisquals.setText(getString(R.string.disquals_text, "" + mTeam.getDisquals()));
    }

    private void updateHanging()
    {
        mHangButton.setText(getString(R.string.hang_text, mTeam.getHangString()));
    }

    private String getTeamSummary()
    {
        String solvedString = null;
        if(mTeam.isSolved())
        {
            solvedString = getString(R.string.team_report_cubes);
        }
        else
        {
            solvedString = getString(R.string.team_report_no_cubes);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mTeam.getDate()).toString();

        String suspect = mTeam.getSuspect();
        if(suspect == null)
        {
            suspect = getString(R.string.team_report_no_contact);
        }
        else
        {
            suspect = getString(R.string.team_report_contact, suspect);
        }

        String report = getString(R.string.team_report, mTeam.getTitle(), dateString, solvedString, suspect);
        return report;
    }

    private void updatePhotoView()
    {
        if(mPhotoFile == null || !mPhotoFile.exists())
        {
            mPhotoView.setImageDrawable(null);
        }
        else
        {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), mPhotoViewSize.x, mPhotoViewSize.y);
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}
