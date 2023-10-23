import React from 'react'
import SaveIcon from "../media/floppy-disk.png"
import { useNavigate } from 'react-router-dom'

const NewPass = () => {
    const navigate = useNavigate()
    return (
        <div className='bg-light w-100 p-4'>
            <h3 className='font-weight-bold'> Change Password </h3>
            <div className=' d-flex flex-column gap-3 align-content-center justify-content-center h-75 '>
                <div className='w-50 align-self-center'>
                    <label htmlFor='password'> Old Password </label>
                    <input type='password' className='form-control' required placeholder='Old Password' />
                </div>
                <div className='w-50 align-self-center'>
                    <label htmlFor='password'> New Password </label>
                    <input type='password' className='form-control' required placeholder='New Password' />
                </div>
                <div className='w-50 align-self-center'>
                    <label htmlFor='password'> New Password Repeat </label>
                    <input type='password' className='form-control' required placeholder='New Password Repeat ' />
                </div>
                <div className='w-50 align-self-center d-flex justify-content-end gap-3'>
                    <button  onClick={()=>navigate("/")} className='btn btn-white text-primary  '> Cancel </button>
                    <button className='btn btn-primary '> Save <img className="p-1" style={{ width: "25px", height: "25px" }} src={SaveIcon} alt='Save' /></button>
                </div>
            </div>

        </div>
    )
}

export default NewPass