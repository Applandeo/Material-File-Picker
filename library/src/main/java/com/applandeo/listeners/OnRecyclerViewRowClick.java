package com.applandeo.listeners;

import java.io.File;

/**
 * Interface response to handle click on files list row
 * <p>
 * Created by Mateusz Kornakiewicz on 01.08.2017.
 */

public interface OnRecyclerViewRowClick {
    void onClick(File file);
}
