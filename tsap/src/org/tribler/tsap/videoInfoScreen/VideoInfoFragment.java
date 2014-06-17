package org.tribler.tsap.videoInfoScreen;

import org.tribler.tsap.R;
import org.tribler.tsap.downloads.XMLRPCDownloadManager;
import org.tribler.tsap.thumbgrid.ThumbItem;
import org.videolan.vlc.gui.video.VideoPlayerActivity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Fragment that shows the detailed info belonging to the selected torrent in
 * the thumb grid
 * 
 * @author Niels Spruit
 */
public class VideoInfoFragment extends Fragment {

	private ThumbItem thumbData;
	private View view;
	private Context context;
	private View.OnClickListener mViewButtonOnClickListener;

	/**
	 * Initializes the video info fragment's layout according to the selected
	 * torrent
	 * 
	 * @param inflater
	 *            The inflater used to inflate the video info layout
	 * @param container
	 *            The container view of this fragment
	 * @param savedInstanceState
	 *            The state of the saved instance
	 * @return The created view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_video_info, container, false);
		context = container.getContext();
		if (getArguments() != null)
			thumbData = (ThumbItem)getArguments().getSerializable("thumbData");

		setValues();
		return view;
	}

	/**
	 * Changes the values of the views according to the torrent metadata
	 */
	private void setValues() {
		TextView title = (TextView) view.findViewById(R.id.video_info_title);
		title.setText(thumbData.getTitle());

		TextView type = (TextView) view.findViewById(R.id.video_details_type);
		type.setText("Video");

		TextView date = (TextView) view
				.findViewById(R.id.video_details_upload_date);
		date.setText("5-11-1998");

		TextView size = (TextView) view
				.findViewById(R.id.video_details_filesize);
		size.setText(String.valueOf(thumbData.getSize()));

		TextView seeders = (TextView) view
				.findViewById(R.id.video_details_seeders);
		seeders.setText(String.valueOf(1));

		TextView leechers = (TextView) view
				.findViewById(R.id.video_details_leechers);
		leechers.setText(String.valueOf(2));

		TextView descr = (TextView) view
				.findViewById(R.id.video_info_description);
		descr.setText("Blabla bla");

		ImageView thumb = (ImageView) view
				.findViewById(R.id.video_info_thumbnail);
		loadBitmap(thumbData.getThumbnailId(), thumb);
		setViewButtonListener();
	}

	/**
	 * Sets the listener of the 'Play video' button to a listener that starts
	 * VLC when the button is pressed
	 */
	private void setViewButtonListener() {
		Button viewButton = (Button) view
				.findViewById(R.id.video_info_stream_video);
		mViewButtonOnClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String URL = "http://inventos.ru/video/sintel/sintel-q3.mp4";
				Uri link = Uri.parse(URL);// Uri.fromFile(f);
				Intent intent = new Intent(Intent.ACTION_VIEW, link,
						getActivity().getApplicationContext(),
						VideoPlayerActivity.class);
				startActivity(intent);
			}
		};
		viewButton.setOnClickListener(mViewButtonOnClickListener);
		
		Button downloadButton = (Button) view
				.findViewById(R.id.video_info_download_video);
		mViewButtonOnClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				XMLRPCDownloadManager.getInstance().downloadTorrent(thumbData.getInfoHash(), thumbData.getTitle());
			}
		};
		downloadButton.setOnClickListener(mViewButtonOnClickListener);
	}

	/**
	 * Loads the thumbnail of the selected torrent
	 * 
	 * @param resId
	 *            The resource id of the thumbnail
	 * @param mImageView
	 *            The ImageView in which the thumbnail should be loaded
	 */
	private void loadBitmap(int resId, ImageView mImageView) {
		float dens = context.getResources().getDisplayMetrics().density;
		int thumbWidth = (int) (100 * dens);
		int thumbHeight = (int) (150 * dens);
		Picasso.with(context).load(resId).placeholder(R.drawable.default_thumb)
				.resize(thumbWidth, thumbHeight).into(mImageView);
	}

}