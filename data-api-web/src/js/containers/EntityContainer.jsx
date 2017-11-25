import React from 'react';
import { connect } from 'react-redux';
import EntityComponent from "../components/EntityComponent";
import { createEntity } from '../actions/EntityActions';
import { getAccessToken } from '../selectors/AuthSelectors';

class EntityContainer extends React.Component {
    render() {
        const {token, onCreateEntity, done} = this.props;
        return <EntityComponent
            done={done}
            handleCreateEntitySubmit={values => {
                onCreateEntity(token, values.databaseName, values.datasetName, values.entityType, values.attributes);
            }} />
    }
}

const mapDispatchToProps = dispatch => {
    return {
        onCreateEntity(token, databaseName, datasetName, entityType, attributes) {
            dispatch(createEntity(token, databaseName, datasetName, entityType, attributes));
        }
    };
};

const mapStateToProps = (state) => {
    return {
        token: getAccessToken(state),
        done: state.entity.get('fetched')
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(EntityContainer);
