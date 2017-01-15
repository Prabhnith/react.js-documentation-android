package company.yak.reactjsdocumentation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static String fileName = "hello-world.html";
    private static String layoutDocumentation;
    private AdView mAdView;

    Context mContext;
    Toolbar mToolbar;
    DrawerLayout mDrawer;
    NavigationView mNavigationView;
    WebView mWebView;
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mWebView = (WebView) findViewById(R.id.web_view_documentation);

        setSupportActionBar(mToolbar);
        mNavigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading: " + url);

                if (url.startsWith("http")) {
                    CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();
                    tabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
                } else {
                    String fileName = url.replace("file:///android_asset/", "");
                    onDocumentationItemSelected(fileName);
                }

                return true;
            }
        });

        try {
            this.layoutDocumentation = readStream(getAssets().open("index.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        onDocumentationItemSelected(this.fileName);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setVisibility(View.INVISIBLE);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Thank you for your support", Snackbar.LENGTH_LONG)
                        .show();

                mFab.hide();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quick_start_installation:
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Quick Start");
                onDocumentationItemSelected("installation.html");
                break;
            case R.id.quick_start_hello_world:
                getSupportActionBar().setTitle("Hello World");
                getSupportActionBar().setSubtitle("Quick Start");
                onDocumentationItemSelected("hello-world.html");
                break;
            case R.id.quick_start_introducing_jsx:
                getSupportActionBar().setTitle("Introducing JSX");
                getSupportActionBar().setSubtitle("Quick Start");
                onDocumentationItemSelected("introducing-jsx.html");
                break;
            case R.id.quick_start_rendering_elements:
                getSupportActionBar().setTitle("Rendering Elements");
                getSupportActionBar().setSubtitle("Quick Start");
                onDocumentationItemSelected("rendering-elements.html");
                break;
            case R.id.quick_start_components_and_props:
                getSupportActionBar().setTitle("Components and Props");
                getSupportActionBar().setSubtitle("Quick Start");
                onDocumentationItemSelected("components-and-props.html");
                break;
            case R.id.quick_start_state_and_lifecycle:
                getSupportActionBar().setTitle("State and Lifecycle");
                getSupportActionBar().setSubtitle("Quick Start");
                onDocumentationItemSelected("state-and-lifecycle.html");
                break;
            case R.id.quick_start_handling_events:
                getSupportActionBar().setTitle("Handling Events");
                getSupportActionBar().setSubtitle("Quick Start");
                onDocumentationItemSelected("handling-events.html");
                break;
            case R.id.quick_start_conditional_rendering:
                getSupportActionBar().setTitle("Conditional Rendering");
                getSupportActionBar().setSubtitle("Quick Start");
                onDocumentationItemSelected("conditional-rendering.html");
                break;
            case R.id.quick_start_lists_and_keys:
                getSupportActionBar().setTitle("Lists and Keys");
                getSupportActionBar().setSubtitle("Quick Start");
                onDocumentationItemSelected("lists-and-keys.html");
                break;
            case R.id.quick_start_forms:
                getSupportActionBar().setTitle("Forms");
                getSupportActionBar().setSubtitle("Quick Start");
                onDocumentationItemSelected("forms.html");
                break;
            case R.id.quick_start_lifting_state_up:
                getSupportActionBar().setTitle("Lifting State Up");
                getSupportActionBar().setSubtitle("Quick Start");
                onDocumentationItemSelected("lifting-state-up.html");
                break;
            case R.id.quick_start_composition_vs_inheritance:
                getSupportActionBar().setTitle("Composition vs Inheritance");
                getSupportActionBar().setSubtitle("Quick Start");
                onDocumentationItemSelected("composition-vs-inheritance.html");
                break;
            case R.id.quick_start_thinking_in_react:
                getSupportActionBar().setTitle("Thinking In React");
                getSupportActionBar().setSubtitle("Quick Start");
                onDocumentationItemSelected("thinking-in-react.html");
                break;
            case R.id.advanced_guides_jsx_in_depth:
                getSupportActionBar().setTitle("JSX In Depth");
                getSupportActionBar().setSubtitle("Advanced Guides");
                onDocumentationItemSelected("jsx-in-depth.html");
                break;
            case R.id.advanced_guides_typechecking_with_proptypes:
                getSupportActionBar().setTitle("Typechecking With PropTypes");
                getSupportActionBar().setSubtitle("Advanced Guides");
                onDocumentationItemSelected("typechecking-with-proptypes.html");
                break;
            case R.id.advanced_guides_refs_and_the_dom:
                getSupportActionBar().setTitle("Refs and the DOM");
                getSupportActionBar().setSubtitle("Advanced Guides");
                onDocumentationItemSelected("refs-and-the-dom.html");
                break;
            case R.id.advanced_guides_uncontrolled_components:
                getSupportActionBar().setTitle("Uncontrolled Components");
                getSupportActionBar().setSubtitle("Advanced Guides");
                onDocumentationItemSelected("uncontrolled-components.html");
                break;
            case R.id.advanced_guides_optimizing_performance:
                getSupportActionBar().setTitle("Optimizing Performance");
                getSupportActionBar().setSubtitle("Advanced Guides");
                onDocumentationItemSelected("optimizing-performance.html");
                break;
            case R.id.advanced_guides_react_without_es6:
                getSupportActionBar().setTitle("React Without ES6");
                getSupportActionBar().setSubtitle("Advanced Guides");
                onDocumentationItemSelected("react-without-es6.html");
                break;
            case R.id.advanced_guides_react_without_jsx:
                getSupportActionBar().setTitle("React Without JSX");
                getSupportActionBar().setSubtitle("Advanced Guides");
                onDocumentationItemSelected("react-without-jsx.html");
                break;
            case R.id.advanced_guides_reconciliation:
                getSupportActionBar().setTitle("Reconciliation");
                getSupportActionBar().setSubtitle("Advanced Guides");
                onDocumentationItemSelected("reconciliation.html");
                break;
            case R.id.advanced_guides_context:
                getSupportActionBar().setTitle("Context");
                getSupportActionBar().setSubtitle("Advanced Guides");
                onDocumentationItemSelected("context.html");
                break;
            case R.id.advanced_guides_higher_order_components:
                getSupportActionBar().setTitle("Web Components");
                getSupportActionBar().setSubtitle("Advanced Guides");
                onDocumentationItemSelected("higher-order-components.html");
                break;
            case R.id.reference_react:
                getSupportActionBar().setTitle("React");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("reference-react.html");
                break;
            case R.id.reference_react_component:
                getSupportActionBar().setTitle("React.Component");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("reference-react-component.html");
                break;
            case R.id.reference_reactdom:
                getSupportActionBar().setTitle("ReactDOM");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("reference-react-dom.html");
                break;
            case R.id.reference_reactdomserver:
                getSupportActionBar().setTitle("ReactDOMServer");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("reference-dom-server.html");
                break;
            case R.id.reference_dom_elements:
                getSupportActionBar().setTitle("DOM Elements");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("reference-dom-elements.html");
                break;
            case R.id.reference_syntheticevent:
                getSupportActionBar().setTitle("SyntheticEvent");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("reference-events.html");
                break;
            case R.id.reference_add_ons:
                getSupportActionBar().setTitle("Add-Ons");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("addons.html");
                break;
            case R.id.reference_performance_tools:
                getSupportActionBar().setTitle("Performance Tools");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("addons-perf.html");
                break;
            case R.id.reference_test_utilities:
                getSupportActionBar().setTitle("Test Utilities");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("addons-test-utils.html");
                break;
            case R.id.reference_animation:
                getSupportActionBar().setTitle("Animation");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("addons-animation.html");
                break;
            case R.id.reference_keyed_fragments:
                getSupportActionBar().setTitle("Keyed Fragments");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("addons-create-fragment.html");
                break;
            case R.id.reference_immutability_helpers:
                getSupportActionBar().setTitle("Immutability Helpers");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("addons-update.html");
                break;
            case R.id.reference_purerendermixin:
                getSupportActionBar().setTitle("PureRenderMixin");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("addons-pure-render-mixin.html");
                break;
            case R.id.reference_shallow_compare:
                getSupportActionBar().setTitle("Shallow Compare");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("addons-shallow-compare.html");
                break;
            case R.id.reference_two_way_binding_helpers:
                getSupportActionBar().setTitle("Two-way Binding Helpers");
                getSupportActionBar().setSubtitle("Reference");
                onDocumentationItemSelected("addons-two-way-binding-helpers.html");
                break;
            case R.id.contributing_how_to_contribute:
                getSupportActionBar().setTitle("How to Contribute");
                getSupportActionBar().setSubtitle("Contributing");
                onDocumentationItemSelected("how-to-contribute.html");
                break;
            case R.id.contributing_codebase_overview:
                getSupportActionBar().setTitle("Codebase Overview");
                getSupportActionBar().setSubtitle("Contributing");
                onDocumentationItemSelected("codebase-overview.html");
                break;
            case R.id.contributing_implementation_notes:
                getSupportActionBar().setTitle("Implementation Notes");
                getSupportActionBar().setSubtitle("Contributing");
                onDocumentationItemSelected("implementation-notes.html");
                break;
            case R.id.contributing_design_principles:
                getSupportActionBar().setTitle("Design Principles");
                getSupportActionBar().setSubtitle("Contributing");
                onDocumentationItemSelected("design-principles.html");
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onDocumentationItemSelected(String fileName) {
        this.fileName = fileName;

        switch (fileName) {
            case "installation.html":
                getSupportActionBar().setTitle("Installation");
                getSupportActionBar().setSubtitle("Quick Start");
                mNavigationView.setCheckedItem(R.id.quick_start_installation);
                break;
            case "hello-world.html":
                getSupportActionBar().setTitle("Hello World");
                getSupportActionBar().setSubtitle("Quick Start");
                mNavigationView.setCheckedItem(R.id.quick_start_hello_world);
                break;
            case "introducing-jsx.html":
                getSupportActionBar().setTitle("Introducing JSX");
                getSupportActionBar().setSubtitle("Quick Start");
                mNavigationView.setCheckedItem(R.id.quick_start_introducing_jsx);
                break;
            case "rendering-elements.html":
                getSupportActionBar().setTitle("Rendering Elements");
                getSupportActionBar().setSubtitle("Quick Start");
                mNavigationView.setCheckedItem(R.id.quick_start_rendering_elements);
                break;
            case "components-and-props.html":
                getSupportActionBar().setTitle("Components and Props");
                getSupportActionBar().setSubtitle("Quick Start");
                mNavigationView.setCheckedItem(R.id.quick_start_components_and_props);
                break;
            case "state-and-lifecycle.html":
                getSupportActionBar().setTitle("State and Lifecycle");
                getSupportActionBar().setSubtitle("Quick Start");
                mNavigationView.setCheckedItem(R.id.quick_start_state_and_lifecycle);
                break;
            case "handling-events.html":
                getSupportActionBar().setTitle("Handling Events");
                getSupportActionBar().setSubtitle("Quick Start");
                mNavigationView.setCheckedItem(R.id.quick_start_handling_events);
                break;
            case "conditional-rendering.html":
                getSupportActionBar().setTitle("Conditional Rendering");
                getSupportActionBar().setSubtitle("Quick Start");
                mNavigationView.setCheckedItem(R.id.quick_start_conditional_rendering);
                break;
            case "lists-and-keys.html":
                getSupportActionBar().setTitle("Lists and Keys");
                getSupportActionBar().setSubtitle("Quick Start");
                mNavigationView.setCheckedItem(R.id.quick_start_lists_and_keys);
                break;
            case "forms.html":
                getSupportActionBar().setTitle("Forms");
                getSupportActionBar().setSubtitle("Quick Start");
                mNavigationView.setCheckedItem(R.id.quick_start_forms);
                break;
            case "lifting-state-up.html":
                getSupportActionBar().setTitle("Lifting State Up");
                getSupportActionBar().setSubtitle("Quick Start");
                mNavigationView.setCheckedItem(R.id.quick_start_lifting_state_up);
                break;
            case "composition-vs-inheritance.html":
                getSupportActionBar().setTitle("Composition vs Inheritance");
                getSupportActionBar().setSubtitle("Quick Start");
                mNavigationView.setCheckedItem(R.id.quick_start_composition_vs_inheritance);
                break;
            case "thinking-in-react.html":
                getSupportActionBar().setTitle("Thinking In React");
                getSupportActionBar().setSubtitle("Quick Start");
                mNavigationView.setCheckedItem(R.id.quick_start_thinking_in_react);
                break;
            case "jsx-in-depth.html":
                getSupportActionBar().setTitle("JSX In Depth");
                getSupportActionBar().setSubtitle("Advanced Guides");
                mNavigationView.setCheckedItem(R.id.advanced_guides_jsx_in_depth);
                break;
            case "typechecking-with-proptypes.html":
                getSupportActionBar().setTitle("Typechecking With PropTypes");
                getSupportActionBar().setSubtitle("Advanced Guides");
                mNavigationView.setCheckedItem(R.id.advanced_guides_typechecking_with_proptypes);
                break;
            case "refs-and-the-dom.html":
                getSupportActionBar().setTitle("Refs and the DOM");
                getSupportActionBar().setSubtitle("Advanced Guides");
                mNavigationView.setCheckedItem(R.id.advanced_guides_refs_and_the_dom);
                break;
            case "uncontrolled-components.html":
                getSupportActionBar().setTitle("Uncontrolled Components");
                getSupportActionBar().setSubtitle("Advanced Guides");
                mNavigationView.setCheckedItem(R.id.advanced_guides_uncontrolled_components);
                break;
            case "optimizing-performance.html":
                getSupportActionBar().setTitle("Optimizing Performance");
                getSupportActionBar().setSubtitle("Advanced Guides");
                mNavigationView.setCheckedItem(R.id.advanced_guides_optimizing_performance);
                break;
            case "react-without-es6.html":
                getSupportActionBar().setTitle("React Without ES6");
                getSupportActionBar().setSubtitle("Advanced Guides");
                mNavigationView.setCheckedItem(R.id.advanced_guides_react_without_es6);
                break;
            case "react-without-jsx.html":
                getSupportActionBar().setTitle("React Without JSX");
                getSupportActionBar().setSubtitle("Advanced Guides");
                mNavigationView.setCheckedItem(R.id.advanced_guides_react_without_jsx);
                break;
            case "reconciliation.html":
                getSupportActionBar().setTitle("Reconciliation");
                getSupportActionBar().setSubtitle("Advanced Guides");
                mNavigationView.setCheckedItem(R.id.advanced_guides_reconciliation);
                break;
            case "context.html":
                getSupportActionBar().setTitle("Context");
                getSupportActionBar().setSubtitle("Advanced Guides");
                mNavigationView.setCheckedItem(R.id.advanced_guides_context);
                break;
            case "higher-order-components.html":
                getSupportActionBar().setTitle("Web Components");
                getSupportActionBar().setSubtitle("Advanced Guides");
                mNavigationView.setCheckedItem(R.id.advanced_guides_higher_order_components);
                break;
            case "reference-react.html":
                getSupportActionBar().setTitle("React");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_react);
                break;
            case "reference-react-component.html":
                getSupportActionBar().setTitle("React.Component");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_react_component);
                break;
            case "reference-react-dom.html":
                getSupportActionBar().setTitle("ReactDOM");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_reactdom);
                break;
            case "reference-dom-server.html":
                getSupportActionBar().setTitle("ReactDOMServer");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_reactdomserver);
                break;
            case "reference-dom-elements.html":
                getSupportActionBar().setTitle("DOM Elements");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_dom_elements);
                break;
            case "reference-events.html":
                getSupportActionBar().setTitle("SyntheticEvent");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_syntheticevent);
                break;
            case "addons.html":
                getSupportActionBar().setTitle("Add-Ons");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_add_ons);
                break;
            case "addons-perf.html":
                getSupportActionBar().setTitle("Performance Tools");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_performance_tools);
                break;
            case "addons-test-utils.html":
                getSupportActionBar().setTitle("Test Utilities");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_test_utilities);
                break;
            case "addons-animation.html":
                getSupportActionBar().setTitle("Animation");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_animation);
                break;
            case "addons-create-fragment.html":
                getSupportActionBar().setTitle("Keyed Fragments");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_keyed_fragments);
                break;
            case "addons-update.html":
                getSupportActionBar().setTitle("Immutability Helpers");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_immutability_helpers);
                break;
            case "addons-pure-render-mixin.html":
                getSupportActionBar().setTitle("PureRenderMixin");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_purerendermixin);
                break;
            case "addons-shallow-compare.html":
                getSupportActionBar().setTitle("Shallow Compare");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_shallow_compare);
                break;
            case "addons-two-way-binding-helpers.html":
                getSupportActionBar().setTitle("Two-way Binding Helpers");
                getSupportActionBar().setSubtitle("Reference");
                mNavigationView.setCheckedItem(R.id.reference_two_way_binding_helpers);
                break;
            case "how-to-contribute.html":
                getSupportActionBar().setTitle("How to Contribute");
                getSupportActionBar().setSubtitle("Contributing");
                mNavigationView.setCheckedItem(R.id.contributing_how_to_contribute);
                break;
            case "codebase-overview.html":
                getSupportActionBar().setTitle("Codebase Overview");
                getSupportActionBar().setSubtitle("Contributing");
                mNavigationView.setCheckedItem(R.id.contributing_codebase_overview);
                break;
            case "implementation-notes.html":
                getSupportActionBar().setTitle("Implementation Notes");
                getSupportActionBar().setSubtitle("Contributing");
                mNavigationView.setCheckedItem(R.id.contributing_implementation_notes);
                break;
            case "design-principles.html":
                getSupportActionBar().setTitle("Design Principles");
                getSupportActionBar().setSubtitle("Contributing");
                mNavigationView.setCheckedItem(R.id.contributing_design_principles);
                break;
        }

        String htmlDocumentation = null;
        try {
            htmlDocumentation = readStream(getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mWebView.loadDataWithBaseURL("file:///android_asset/",
                this.layoutDocumentation.replace("{documentation}", htmlDocumentation), "text/html", "UTF-8", "");
    }

    @NonNull
    private String readStream(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
