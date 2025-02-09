const apiUrl = 'http://localhost:8080/myblog/posts';

let currentPage = 0;
let postsPerPage = 10;

document.addEventListener('DOMContentLoaded', () => {
    loadPosts();
    document.getElementById('nextPageBtn').addEventListener('click', nextPage);
    document.getElementById('prevPageBtn').addEventListener('click', prevPage);
    document.getElementById('addPostBtn').addEventListener('click', openAddPostForm);
});

async function loadPosts() {
    const response = await fetch(`${apiUrl}?page=${currentPage}&size=${postsPerPage}`);
    const posts = await response.json();
    renderPosts(posts);
}

function renderPosts(posts) {
    const postsList = document.getElementById('postsList');
    postsList.innerHTML = '';

    posts.forEach(post => {
        const postElement = document.createElement('div');
        postElement.className = 'post';
        postElement.innerHTML = `
            <h2><a href="post.html?id=${post.id}">${post.title}</a></h2>
            <img src="${post.imgUrl}" alt="${post.title}">
            <p>${post.content.substring(0, 150)}...</p>
            <p>Теги: ${post.tags}</p>
            <p>Лайков: ${post.likeCount}</p>
        `;
        postsList.appendChild(postElement);
    });
}

function nextPage() {
    currentPage++;
    loadPosts();
    updatePageNumber();
}

function prevPage() {
    if (currentPage > 0) {
        currentPage--;
        loadPosts();
        updatePageNumber();
    }
}

function updatePageNumber() {
    document.getElementById('pageNumber').innerText = currentPage + 1;
}

function openAddPostForm() {
    window.location.href = 'addPost.html';
}
