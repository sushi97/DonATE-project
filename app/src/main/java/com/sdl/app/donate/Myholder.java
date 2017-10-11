package com.sdl.app.donate;

import android.view.View;
import android.widget.EditText;

/**
 * Created by tanmay on 10/10/17.
 */

public class Myholder
{


    EditText Name,Email,Phone,City,Location;

    public Myholder(View itemView){

       // Name=(EditText)itemView.findViewById( R.id.name );
        Email=(EditText)itemView.findViewById( R.id.email );
        Phone=(EditText)itemView.findViewById( R.id.phone);
        City=(EditText)itemView.findViewById( R.id.city);
        Location=(EditText)itemView.findViewById( R.id.location);
    }

}
