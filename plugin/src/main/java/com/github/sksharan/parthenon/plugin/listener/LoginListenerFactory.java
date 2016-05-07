package com.github.sksharan.parthenon.plugin.listener;

import com.github.sksharan.parthenon.plugin.ParthenonPlugin;

public interface LoginListenerFactory {
    public LoginListener create(ParthenonPlugin plugin);
}
