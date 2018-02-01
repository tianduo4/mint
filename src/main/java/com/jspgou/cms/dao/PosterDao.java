package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Poster;

import java.util.List;

public abstract interface PosterDao {
    public abstract Poster findById(Integer paramInteger);

    public abstract Poster saveOrUpdate(Poster paramPoster);

    public abstract Poster update(Poster paramPoster);

    public abstract Poster deleteById(Integer paramInteger);

    public abstract List<Poster> getPage();
}

