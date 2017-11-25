import React from 'react';
import ReactDOM from "react-dom";

class RenderDropDown extends React.Component {

    constructor(props) {
        super(props);
        this.handleOnChange = this.handleOnChange.bind(this);
    }

    componentDidMount() {
        $(ReactDOM.findDOMNode(this.refs.selectRef)).material_select();
        $(ReactDOM.findDOMNode(this.refs.selectRef)).on('change', this.handleOnChange);
    }

    componentDidUpdate(prevProps) {
        if (this.props.options !== prevProps.options) {
            $(ReactDOM.findDOMNode(this.refs.selectRef)).material_select();
        }
    }

    handleOnChange() {
        let newValue = $(ReactDOM.findDOMNode(this.refs.selectRef)).val()
        this.props.input.onChange(newValue);
    }

    render() {
        const { input, label, id, options, smallSize, mediumSize, largeSize, withContainer, meta: { touched, error, warning } } = this.props;
        return withContainer === true ? <div class="row">
            <div class={`input-field col ${smallSize} ${mediumSize} ${largeSize}`}>
                <select id={id} name={id} ref="selectRef">
                    <option value="" disabled selected>Choose your option</option>
                    {
                        options.map(op => <option key={op.value} value={op.value}>{op.text}</option>)
                    }
                </select>
                <label>{label}</label>
            </div>
        </div> : <div class={`input-field col ${smallSize} ${mediumSize} ${largeSize}`}>
                <select id={id} name={id} ref="selectRef">
                    <option value="" disabled selected>Choose your option</option>
                    {
                        options.map(op => <option key={op.value} value={op.value}>{op.text}</option>)
                    }
                </select>
                <label>{label}</label>
            </div>
    }
}

RenderDropDown.propTypes = {
    input: React.PropTypes.object.isRequired,
    label: React.PropTypes.string.isRequired,
    id: React.PropTypes.string.isRequired,
    options: React.PropTypes.array.isRequired,
    smallSize: React.PropTypes.string.isRequired,
    mediumSize: React.PropTypes.string.isRequired,
    largeSize: React.PropTypes.string.isRequired,
    withContainer: React.PropTypes.bool.isRequired
}
RenderDropDown.defaultProps = {
    smallSize: "s12",
    mediumSize: "m12",
    largeSize: "l6",
    withContainer: true
}
export default RenderDropDown;