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
package org.deegree.filter.sql.postgis;

import static java.sql.Types.VARCHAR;
import static org.deegree.commons.tom.primitive.BaseType.DATE;

import java.io.IOException;
import java.net.URL;
import java.sql.Types;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.deegree.commons.tom.primitive.BaseType;
import org.deegree.commons.tom.primitive.PrimitiveType;
import org.deegree.commons.tom.sql.DefaultPrimitiveConverter;
import org.deegree.commons.tom.sql.PrimitiveParticleConverter;
import org.deegree.filter.FilterEvaluationException;
import org.deegree.filter.OperatorFilter;
import org.deegree.filter.expression.PropertyName;
import org.deegree.filter.sql.DBField;
import org.deegree.filter.sql.PropertyNameMapper;
import org.deegree.filter.sql.PropertyNameMapping;
import org.deegree.filter.sql.TableAliasManager;
import org.deegree.filter.sql.UnmappableException;
import org.deegree.filter.sql.expression.SQLExpression;
import org.deegree.filter.xml.Filter110XMLDecoder;
import org.deegree.filter.xml.Filter110XMLDecoderTest;
import org.junit.Test;

import com.vividsolutions.jts.util.Assert;

/**
 * The <code></code> class TODO add class documentation here.
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider</a>
 * @author last edited by: $Author$
 * 
 * @version $Revision$, $Date$
 */
public class PostGISWhereBuilderTest {

    @Test
    public void testFilter1()
                            throws Exception {
        OperatorFilter filter = parse( "testfilter1.xml" );
        PostGISWhereBuilder wb = new PostGISWhereBuilder( new DummyPostGISMapping(), filter, null, true, false );
        SQLExpression whereClause = wb.getWhere();
        Assert.equals( "X1.NAME = 'Albert Camus'", whereClause.toString() );
    }

//    @Test
//    public void testFilter2()
//                            throws Exception {
//        OperatorFilter filter = parse( "testfilter2.xml" );
//        PostGISWhereBuilder wb = new PostGISWhereBuilder( new DummyPostGISMapping(), filter, null, true, false );
//        SQLExpression whereClause = wb.getWhere();
//        Assert.equals( "(X1.DATE_OF_BIRTH > '1820-01-01' AND X1.NAME='Germany')", whereClause.toString() );
//    }
//
//    @Test
//    public void testFilter5()
//                            throws Exception {
//        OperatorFilter filter = parse( "testfilter5.xml" );
//        PostGISWhereBuilder wb = new PostGISWhereBuilder( new DummyPostGISMapping(), filter, null, true, false );
//        SQLExpression whereClause = wb.getWhere();
//        System.out.println( whereClause );
//    }
//
//    @Test
//    public void testFilter6()
//                            throws Exception {
//        OperatorFilter filter = parse( "testfilter6.xml" );
//        PostGISWhereBuilder wb = new PostGISWhereBuilder( new DummyPostGISMapping(), filter, null, true, false );
//        SQLExpression whereClause = wb.getWhere();
//        System.out.println( whereClause );
//    }
//
//    @Test
//    public void testFilter8()
//                            throws Exception {
//        OperatorFilter filter = parse( "testfilter8.xml" );
//        PostGISWhereBuilder wb = new PostGISWhereBuilder( new DummyPostGISMapping(), filter, null, true, false );
//        SQLExpression whereClause = wb.getWhere();
//        System.out.println( whereClause );
//    }
//
//    @Test
//    public void testFilter11()
//                            throws Exception {
//        OperatorFilter filter = parse( "testfilter11.xml" );
//        PostGISWhereBuilder wb = new PostGISWhereBuilder( new DummyPostGISMapping(), filter, null, true, false );
//        SQLExpression whereClause = wb.getWhere();
//        System.out.println( whereClause );
//    }
//
//    @Test
//    public void testFilter15()
//                            throws Exception {
//        OperatorFilter filter = parse( "testfilter15.xml" );
//        PostGISWhereBuilder wb = new PostGISWhereBuilder( new DummyPostGISMapping(), filter, null, true, false );
//        SQLExpression whereClause = wb.getWhere();
//        System.out.println( whereClause );
//    }

    private OperatorFilter parse( String resourceName )
                            throws XMLStreamException, FactoryConfigurationError, IOException {
        URL url = Filter110XMLDecoderTest.class.getResource( "testdata/v110/" + resourceName );
        XMLStreamReader xmlStream = XMLInputFactory.newInstance().createXMLStreamReader( url.toString(),
                                                                                         url.openStream() );
        xmlStream.nextTag();
        xmlStream.getLocation();
        return (OperatorFilter) Filter110XMLDecoder.parse( xmlStream );
    }

    static class DummyPostGISMapping implements PropertyNameMapper {

        @Override
        public PropertyNameMapping getMapping( PropertyName propName, TableAliasManager aliasManager )
                                throws FilterEvaluationException, UnmappableException {
            if ( propName.getAsText().equals( "app:name" ) ) {
                PrimitiveType pt = new PrimitiveType( BaseType.STRING );
                PrimitiveParticleConverter converter = new DefaultPrimitiveConverter( pt, "NAME", false );
                return new PropertyNameMapping( converter, null );
            }
            if ( propName.getAsText().equals( "app:id" ) ) {
                PrimitiveType pt = new PrimitiveType( BaseType.STRING );
                PrimitiveParticleConverter converter = new DefaultPrimitiveConverter( pt, "ID", false );
                return new PropertyNameMapping( converter, null );
            }
            if ( propName.getAsText().equals( "app:subject" ) ) {
                PrimitiveType pt = new PrimitiveType( BaseType.STRING );
                PrimitiveParticleConverter converter = new DefaultPrimitiveConverter( pt, "SUBJECT", false );
                return new PropertyNameMapping( converter, null );                
            }
            if ( propName.getAsText().equals( "app:dateOfBirth" ) ) {
                PrimitiveType pt = new PrimitiveType( BaseType.DATE );
                PrimitiveParticleConverter converter = new DefaultPrimitiveConverter( pt, "DATE_OF_BIRTH", false );                
                return new PropertyNameMapping( converter, null ); 
            }
            if ( propName.getAsText().equals( "app:placeOfBirth/app:Place/app:country/app:Country/app:name") ) {
                PrimitiveType pt = new PrimitiveType( BaseType.STRING );
                PrimitiveParticleConverter converter = new DefaultPrimitiveConverter( pt, "NAME", false );                
                return new PropertyNameMapping( converter, null ); 
            }
            throw new UnmappableException( "Property '" + propName + "' is not mappable." );
        }
    }
}