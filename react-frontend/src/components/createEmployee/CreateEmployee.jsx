import axios from "axios";
import React, {Component} from "react";
import EmployeeService from "../../services/EmployeeService";
import './CreateEmployee.css';

class CreateEmployee extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // id: this.props.match.params.id,
            firstName: '',
            lastName: '',
            email: '',
            message: '',
        }

        this.changeFirstNameHandler = this.changeFirstNameHandler.bind(this);
        this.changeLastNameHandler = this.changeLastNameHandler.bind(this);
        this.saveOrUpdateEmployee = this.saveOrUpdateEmployee.bind(this);
    }
    
    componentDidMount(){
        if (this.state.id === '_add') {
            return
        }else{
            EmployeeService.getEmployeeById(this.state.id).then((res) => {
                let employee = res.data;
                this.setState({firstName: employee.firstName,
                    lastName: employee.lastName,
                    email: employee.email})
            });
        }
    }

    saveOrUpdateEmployee = (e) => {
        e.preventDefault();
        let employee = {firstName: this.state.firstName, lastName: this.state.lastName, emailId: this.state.emailId};
        console.log('employee => ' + JSON.stringify(employee));

        if(this.state.id === '_add'){
            EmployeeService.createEmployee(employee).then(res =>{
                this.props.history.push('/employees');
            });
        }else{
            EmployeeService.updateEmployee(employee, this.state.id).then( res => {
                this.props.history.push('/employees');
            });
        }
    }

    changeFirstNameHandler = (event) => {
        this.setState({firstName: event.target.value});
    }

    changeLastNameHandler = (event) => {
        this.setState({lastName: event.target.value});
    }

    changeEmailHandler = (event) => {
        this.setState({email: event.target.value});
    }

    cancel(){
        this.props.history.push('/employees');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h1>Register New Employee</h1>
        }else{
            return <h1>Register New Employee</h1>
        }
    }
    
    // handleSubmit = (event) => {
    //     alert('Form was submitted: ' + this.state);
    //     let employee = {firstName: this.state.firstName,
    //         lastName: this.state.lastName,
    //         email: this.state.email};

    //     fetch('http://localhost:8080/api/employee',{
    //         method: 'POST',
    //         headers: {
    //             "Content-Type": 'application/json'
    //         },
    //         body: JSON.stringify(employee),
    //         "Access-Control-Allow-Origin": "*",
    //         credentials: 'include'
    //     }).then((res) => {
    //         console.log('employee => ' +JSON.stringify(employee));
    //         this.props.history.push('/employee');
    //         return res.json;
    //     }).then((res) => res.json());
    //     event.preventDefault();
    // }

    handleSubmit = async (e) => {
        e.preventDefault();

        const employee = {
            firstName: this.state.firstName,
            lastName: this. state.lastName,
            email: this.state.email
        }
        await axios.post("http://localhost:8080/api/employee", employee).then(res => {
            if(res.status === 200) {
                alert("Employee Registered Successfully");
                window.location.reload();
            }
        });
        this.setState({
            firstName: "",
            lastName: "",
            email: ""
        })
    }

    render() {
        return(
            <div >
                <br />
                <div className="container">
                    <form className="col-md-5 offset-md-4" >
                       {
                           this.getTitle()
                        }                      
                        <div className="form-group mb-3">
                            <label>First Name* </label>
                            <input type="text" name="firstName" className="form-control"
                               value={this.state.firstName} onChange={this.changeFirstNameHandler}/>
                        </div>

                        <div className="form-group mb-3">
                            <label>Last Name* </label>
                            <input type="text"  name="lastName" className="form-control" 
                               value={this.state.lastName} onChange={this.changeLastNameHandler}/>
                        </div>

                        <div className="form-group mb-3">
                            <label>Email Address* </label>
                            <input type="text"  name="email" className="form-control" 
                                value={this.state.email} onChange={this.changeEmailHandler}/>
                        </div>

                        <button className="btn save" type="submit" onClick={this.handleSubmit}>Save</button>
                        <button className="btn cancel" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                        <div className="message"> <p>{this.state.message}</p> </div>
                    </form>
                </div>
             </div>
        )
    }
}


export default CreateEmployee;