package com.myfarmnow.myfarmcrop.fragments.agronomy;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.myfarmnow.myfarmcrop.R;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

public class CropPDFViewFragment extends Fragment implements OnPageChangeListener, OnLoadCompleteListener {
    private static final String TAG = "CropPDFViewFragment";
    private Context context;
    private Integer pageNumber = 0;
    private String pdfFileName, pageTitle;

    private Toolbar toolbar;
    private PDFView pdfView;
    private TextView textViewPdfViewerLoading;

    public CropPDFViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crop_p_d_f_view, container, false);

        toolbar = view.findViewById(R.id.toolbar_crop_pdf_view);
        pdfView = view.findViewById(R.id.pdfView);
        textViewPdfViewerLoading = view.findViewById(R.id.text_view_pdf_viewer_loading);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        if (!getArguments().getString("fileName").isEmpty()) {
            displayFromAsset(getArguments().getString("fileName"));
            pageTitle = getArguments().getString("pageTitle");
        } else {
            requireActivity().finish();
        }

        requireActivity().setTitle(String.format("%s", pageTitle));
    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;
        pdfView.fromAsset(pdfFileName)
                .defaultPage(pageNumber)
                .enableSwipe(true)

                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(context))
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        //setTitle(String.format("%s %s / %s", pageTitle, page + 1, pageCount));
        textViewPdfViewerLoading.setVisibility(View.GONE);
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e("Book", String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }
}
