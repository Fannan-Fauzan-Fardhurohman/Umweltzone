package de.avpptr.umweltzone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.models.Faq;

public class FaqsAdapter extends BaseExpandableListAdapter {

    private final Context mContext;
    private final List<Faq> mFaqs;

    public FaqsAdapter(Context context, List<Faq> faqs) {
        mContext = context;
        mFaqs = faqs;
    }

    @Override
    public int getGroupCount() {
        return mFaqs.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mFaqs.get(groupPosition).question;
    }

    @Override
    public Object getChild(final int groupPosition, int childPosition) {
        final String faqAnswer = mFaqs.get(groupPosition).answer;
        final String faqSourceUrl = mFaqs.get(groupPosition).sourceUrl;
        return new FaqAnswer() {{
            text = faqAnswer;
            sourceUrl = faqSourceUrl;
        }};
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private View getNewView(int resourceId) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(resourceId, null);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            convertView = getNewView(R.layout.faq_list_group);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.faq_question);
        textView.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final FaqAnswer faqAnswer = (FaqAnswer) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = getNewView(R.layout.faq_list_item);
        }

        final String childText = faqAnswer.text;
        TextView textView = (TextView) convertView.findViewById(R.id.faq_answer);
        textView.setText(childText);

        final String sourceUrlText = faqAnswer.sourceUrl;
        TextView sourceUrlView = (TextView) convertView.findViewById(R.id.faq_source_url);
        sourceUrlView.setText(sourceUrlText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class FaqAnswer extends Object {
        String text;
        String sourceUrl;
    }
}
