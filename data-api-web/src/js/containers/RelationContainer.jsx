import React from 'react';
import { connect } from 'react-redux';
import RelationComponent from "../components/RelationComponent";
import { createRelation } from '../actions/RelationActions';
import { getAccessToken } from '../selectors/AuthSelectors';

class RelationContainer extends React.Component {
    render() {
        const {token, onCreateRelation} = this.props;
        return <RelationComponent
            handleCreateRelationSubmit={values => {
                onCreateRelation(token, values.sourceEntityType, values.destinationEntityType, values.sourceDatabaseName, values.sourceDatasetName, values.sourceEntityId, values.destinationDatabaseName, values.destinationDatasetName, values.destinationEntityId, values.relationName);
            }} />
    }
}

const mapDispatchToProps = dispatch => {
    return {
        onCreateRelation(token, sourceType, destinationType, sourceDatabase, sourceDataset, sourceId, destinationDatabase, destinationDataset, destinationId, relationName, relationType) {
            dispatch(createRelation(token, sourceType, destinationType, sourceDatabase, sourceDataset, sourceId, destinationDatabase, destinationDataset, destinationId, relationName, relationType));
        }
    };
};

const mapStateToProps = (state) => {
    return {
        token: getAccessToken(state)
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(RelationContainer);
