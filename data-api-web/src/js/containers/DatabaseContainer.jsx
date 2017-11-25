import React from 'react';
import DatabaseComponent from "../components/DatabaseComponent";
import { connect } from 'react-redux';
import { createDatabase } from '../actions/DatabaseActions';
import { getAccessToken } from '../selectors/AuthSelectors';
import { getResponses } from '../selectors/DatabaseSelectors';

class DatabaseContainer extends React.Component {
    render() {
        const {token, responses, onCreateDatabase} = this.props;
        return <DatabaseComponent
            responses={responses}
            handleCreateDatabaseSubmit={values => {
                onCreateDatabase(token, values.databaseName, values.databaseType);
            }}
            handleDeleteDatabaseSubmit={values => {

            }} />
    }
}

const mapDispatchToProps = dispatch => {
    return {
        onCreateDatabase(token, databaseName, databaseType) {
            dispatch(createDatabase(token, databaseName, databaseType));
        }
    };
};

const mapStateToProps = (state) => {
    return {
        token: getAccessToken(state),
        responses: getResponses(state)
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(DatabaseContainer);
