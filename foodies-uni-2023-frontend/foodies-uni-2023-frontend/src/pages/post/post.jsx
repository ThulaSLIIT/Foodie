import React, {useEffect, useState} from "react";
import Layout from "../../layout/layout";
import {Carousel} from "react-responsive-carousel";
import CardMedia from "@mui/material/CardMedia";
import Card from "@mui/material/Card";
import IconButton from "@mui/material/IconButton";
import FavoriteIcon from "@mui/icons-material/Favorite";
import ChatBubbleIcon from "@mui/icons-material/ChatBubble";
import CardActions from "@mui/material/CardActions";
import TextField from "@mui/material/TextField";
import './post.scss';
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import SendIcon from '@mui/icons-material/Send';
import User from "../../assets/user.png";
import {useNavigate} from "react-router-dom";
import Cookies from "js-cookie";
import * as Api from "../../service/apis";
import swal from "sweetalert";
import EditIcon from '@mui/icons-material/Edit';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';

const Post = () => {

    const navigate = useNavigate();
    const searchParams = new URLSearchParams(document.location.search)

    const [userId, setUserId] = useState(0);
    const [post, setPost] = useState(null);
    const [postId, setPostId] = useState(null);

    const [comment, setComment] = useState("");
    const [comments, setComments] = useState([]);

    useEffect(() => {

        console.log("Cookies: ", (Cookies.get("foodies_user_id")));

        if (!Cookies.get("foodies_user_id")) navigate("/login");
        setUserId(Number(Cookies.get("foodies_user_id")));

        if (searchParams.get('id')) {
            setPostId(searchParams.get('id'));
        } else {
            navigate("/")
        }
    }, [])

    useEffect(() => {
        loadPost();
        getComments();
    }, [postId])

    const loadPost = () => {
        Api.getPostsByPostId(postId).then(r => {
            if (r.success) {
                setPost(r.data.body);
            } else {
                swal("Sorry!", r.data.msg, "error");
            }
        });
    }

    const like = (postId) => {
        Api.manageLikes(postId, userId).then(r => {
            if (r.success) {
                loadPost();
            } else {
                swal("Sorry!", r.data.msg, "error");
            }
        });
    }

    const getComments = () => {
        Api.getCommentsByPostId(postId).then(r => {
            if (r.success) {
                setComments(r.data.body);
            } else {
                swal("Sorry!", r.data.msg, "error");
            }
        });
    }

    const sendComment = () => {
        if (comment) {
            let body = {
                postId: postId,
                userId: userId,
                comment: comment
            }
            Api.addComment(body).then(r => {
                if (r.success) {
                    loadPost();
                    getComments();
                    setComment("");
                } else {
                    swal("Sorry!", r.data.msg, "error");
                }
            });
        } else {
            swal("Invalid Input", "Please enter valid comment", "error");
        }
    }

    const deletePost = () => {

        swal({
            title: "Are you sure?",
            text: "Are you sure to delete this post",
            icon: "warning",
            dangerMode: true,
            showCloseButton: true,
            showCancelButton: true,
            focusConfirm: false,
            confirmButtonText:
                'Ok',
            cancelButtonText:
                'No'
        })
            .then(willDelete => {
                if (willDelete) {
                    Api.deletePost(postId).then(r => {
                        if (r.success) {
                            swal("Success!", r.data.msg, "success");
                            navigate(-1);
                        } else {
                            swal("Sorry!", r.data.msg, "error");
                        }
                    });
                }
            });
    }

    return (
        <Layout>
            {
                post &&
                <section id={"post-bg"}>
                    {
                        userId===post.user.id && <div style={{textAlign: "right"}}>
                            <IconButton aria-label="add to favorites" style={{color:"red"}}>
                                <DeleteForeverIcon/>
                                <span className={'react-counts'} onClick={deletePost}>Delete this post</span>
                            </IconButton>
                            <IconButton aria-label="add to favorites">
                                <EditIcon/>
                                <span className={'react-counts'} onClick={() => navigate(`/post-management?id=${postId}`)}>Edit this post</span>
                            </IconButton>
                        </div>
                    }

                    <Carousel>
                        {
                            post.images.map(img => <CardMedia
                                component="img"
                                alt="green iguana"
                                height="340"
                                image={img}
                            />)
                        }
                    </Carousel>

                    <h1>{post.title}</h1>

                    <div style={{fontSize: '10px'}}>
                        {new Date(post.date).toDateString()}
                    </div>

                    <p>
                        {post.description}
                    </p>

                    <div>

                        <IconButton aria-label="add to favorites">
                            <FavoriteIcon/>
                            <span className={'react-counts'} onClick={() => like(post.id)}>{post.likes}</span>
                        </IconButton>
                        <IconButton aria-label="add to favorites">
                            <ChatBubbleIcon/>
                            <span className={'react-counts'}>{post.comments}</span>
                        </IconButton>

                    </div>

                    <div id={'comment'}>

                        <div>
                            <div style={{marginBottom: '15px'}}>Add your comment here.</div>
                            <TextField
                                label="Comment"
                                id="outlined-size-small"
                                size="small"
                                multiline
                                rows={4}
                                className={"text-fields"}
                                value={comment}
                                onChange={e => setComment(e.target.value)}
                            />
                            <Box component="div" sx={{width: "100%", textAlign: 'end'}}>
                                <Button variant="contained" sx={{marginTop: '10px'}} endIcon={<SendIcon/>}
                                        onClick={sendComment}>
                                    Send
                                </Button>
                            </Box>

                        </div>

                        <div>

                            {
                                comments.map(r => <div className={'post-comment'}>
                                    <div className={'post-comment-user'}>
                                        <img src={User} title="user" alt="user.png" width={50}/>
                                        <h4>{r.user.firstName + " " + r.user.lastName}</h4>
                                    </div>
                                    <p>
                                        {
                                            r.comment
                                        }
                                    </p>
                                </div>)
                            }

                        </div>

                    </div>

                </section>
            }
        </Layout>
    )

}

export default Post;
