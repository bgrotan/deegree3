//$HeadURL$
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2009 by:
 - Department of Geography, University of Bonn -
 and
 - lat/lon GmbH -

 This library is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 Contact information:

 lat/lon GmbH
 Aennchenstr. 19, 53177 Bonn
 Germany
 http://lat-lon.de/

 Department of Geography, University of Bonn
 Prof. Dr. Klaus Greve
 Postfach 1147, 53001 Bonn
 Germany
 http://www.geographie.uni-bonn.de/deegree/

 e-mail: info@deegree.org
 ----------------------------------------------------------------------------*/
package org.deegree.feature.persistence.sql.rules;

import java.util.List;

import org.deegree.commons.tom.primitive.PrimitiveType;
import org.deegree.commons.tom.primitive.PrimitiveValue;
import org.deegree.commons.tom.sql.ParticleConverter;
import org.deegree.feature.persistence.sql.expressions.TableJoin;
import org.deegree.filter.expression.PropertyName;
import org.deegree.filter.sql.MappingExpression;

/**
 * {@link Mapping} of {@link PrimitiveValue} particles.
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider</a>
 * @author last edited by: $Author$
 * 
 * @version $Revision$, $Date$
 */
public class PrimitiveMapping extends Mapping {

    private final PrimitiveType pt;

    private final MappingExpression mapping;

    private final ParticleConverter<PrimitiveValue> converter;

    private final boolean isNullable;

    /**
     * 
     * @param path
     * @param mapping
     * @param pt
     * @param tableChange
     *            table joins, can be <code>null</code> (no joins involved)
     */
    public PrimitiveMapping( PropertyName path, MappingExpression mapping, PrimitiveType pt,
                             List<TableJoin> tableChange, ParticleConverter<PrimitiveValue> converter,
                             boolean isNullable ) {
        super( path, tableChange );
        this.pt = pt;
        this.mapping = mapping;
        this.converter = converter;
        this.isNullable = isNullable;
    }

    /**
     * Returns the primitive type for the mapped particles.
     * 
     * @return primitive type for the mapped particles
     */
    public PrimitiveType getType() {
        return pt;
    }

    public MappingExpression getMapping() {
        return mapping;
    }

    public ParticleConverter<PrimitiveValue> getConverter() {
        return converter;
    }

    /**
     * Returns whether the particle can simply be omitted (or if a missing value needs further action, such as NULL
     * escalation).
     * 
     * @return true, if the particle can be omitted, false otherwise
     */
    public boolean isNullable() {
        return isNullable;
    }
}