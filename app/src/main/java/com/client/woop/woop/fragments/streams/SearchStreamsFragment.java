package com.client.woop.woop.fragments.streams;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.client.woop.woop.R;
import com.client.woop.woop.adapter.TuneInAdapter;
import com.client.woop.woop.controller.SearchStreamsController;
import com.client.woop.woop.fragments.BaseFragment;
import com.client.woop.woop.fragments.interfaces.ISearchStreamsView;
import com.client.woop.woop.models.TuneInModel;
import com.client.woop.woop.models.YouTubeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchStreamsFragment extends BaseFragment
        implements ISearchStreamsView,
        RecognitionListener
{

    private SearchStreamsController _controller;
    private ListView _listview;
    private EditText _text;
    private Button _searchButton;
    private Button _voiceButton;
    private SpeechRecognizer _speech;


    public static SearchStreamsFragment newInstance() {
        SearchStreamsFragment fragment = new SearchStreamsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_streams_search, container, false);


        _listview = (ListView) v.findViewById(R.id.fragment_streams_search_results);
        _text = (EditText) v.findViewById(R.id.fragment_streams_search_query);

        _searchButton = (Button) v.findViewById(R.id.fragment_streams_search_button_search);
        _searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.search();
            }
        });

        _voiceButton = (Button) v.findViewById(R.id.fragment_streams_voice_button);
        _voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                _speech.startListening(intent);

                showToast("Start speaking ...");
            }
        });


        _listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _controller.play(position);
            }
        });

        _controller = new SearchStreamsController(this, woopServer());

        _speech = SpeechRecognizer.createSpeechRecognizer(getActivity());
        _speech.setRecognitionListener(this);

        return v;
    }

    @Override
    public String getSearchString() {
        return _text.getText().toString();
    }

    @Override
    public void setSearchResult(List<TuneInModel> result) {
        Activity activity = getActivity();
        if(activity == null) return;
        _listview.setAdapter(new TuneInAdapter(activity, result));
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showProgress(String title, String message) {
        super.showProgressbar(title, message);
    }

    @Override
    public void hideProgress() {
        super.hideProgressbar();
    }


    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> words = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if(words.size() > 0) {
            _text.setText(words.get(0));
            _controller.search();
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
