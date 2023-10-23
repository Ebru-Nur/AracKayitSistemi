import React, { useState } from 'react'
import Show from "../media/show.png"
import { Link } from 'react-router-dom'

const Login = () => {
    const [data, setData] = useState({username:"",password:""})
    const [visible, setVisible] = useState(false)
    const handleVisible = () => {setVisible(!visible)
    
    } 
    console.log(data)
    return (
        <div className='login template d-flex justify-content-center align-items-center 100-w vh-100 bg-light'>
            <div className='40-w p-5 rounded bg-white shadow'>
                <form className='d-flex flex-column gap-3'>
                    <h2 className='text-center'> EBSA CARS </h2>
                    <h5 className='text-secondary text-center'> Please log in to continue </h5>
                    <div className='mb-2'>
                        <label htmlFor='username'> Username </label>
                        <input type="username" value={data.username} onChange={(e)=> setData({...data,username:e.target.value})} required placeholder='Username' className='form-control' />
                    </div>
                    <div className='mb-2'>
                        <label htmlFor='password'> Password </label>
                        <div className='d-flex'>
                            <input type={visible ? "text" : "password"} value={data.password} onChange={(e)=> setData({...data,password:e.target.value})} required placeholder='Password' className='form-control' />
                            <div onClick={handleVisible}>
                                <img className='p-2' style={{ width: "35px" }} src={Show} alt='icon' />
                            </div>

                        </div>

                    </div>
                    <div className='d-grid'>
                        <button className='btn btn-primary '> Log in </button>
                    </div>
                    <p className='text-center'>
                        No account yet? <Link to="/signup" className="ms-2"> Sign Up</Link>
                    </p>
                </form>
            </div>
        </div>
    )
}

export default Login