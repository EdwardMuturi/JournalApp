package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.edward.journalapp.R;

import java.util.List;

import model.JournalEntity;

public class JournalEntriesAdapter extends RecyclerView.Adapter<JournalEntriesAdapter.ViewHolder> {
    List<JournalEntity> journalEntityList;
    final private EntryClickListener entryClickListener;
    final private LongEntryClickListener longEntryClickListener;

//     [START] click interfaces
    public interface EntryClickListener{
        void onEntryClickListener(int position);
    }

    public interface LongEntryClickListener{
        void onLongEntryClickListener(int position);
    }

//    [END] click interfaces

    //constructor
    public JournalEntriesAdapter(List<JournalEntity> journalEntityList, EntryClickListener entryClickListener, LongEntryClickListener longEntryClickListener) {
        this.journalEntityList = journalEntityList;
        this.entryClickListener = entryClickListener;
        this.longEntryClickListener = longEntryClickListener;

    }

    @NonNull
    @Override
    public JournalEntriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_journal_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalEntriesAdapter.ViewHolder holder, int position) {
        JournalEntity journalEntity= journalEntityList.get(position);

        holder.tvTitle.setText(journalEntity.getTitle());
        holder.tvBody.setText(journalEntity.getMessage());
    }

    public JournalEntity getItem(int position){
        return journalEntityList.get(position);
    }

    @Override
    public int getItemCount() {
        return journalEntityList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvTitle;
        TextView tvBody;
        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvTitle= itemView.findViewById(R.id.tv_entry_title);
            tvBody= itemView.findViewById(R.id.tv_entry_body);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition= getAdapterPosition();
            entryClickListener.onEntryClickListener(clickedPosition);
        }

        @Override
        public boolean onLongClick(View view) {
            int clickedPosition= getAdapterPosition();
            longEntryClickListener.onLongEntryClickListener(clickedPosition);
            return true;
        }
    }
}
