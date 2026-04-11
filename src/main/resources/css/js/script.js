
//Loading button
<script>
function showLoader(btn) {
    btn.innerHTML = "Processing...";
    btn.disabled = true;
}
</script>

<script>
document.getElementById("embedForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const formData = new FormData(this);

    const response = await fetch("/embed", {
        method: "POST",
        body: formData
    });

    if (!response.ok) {
        alert("Error embedding image");
        return;
    }

    const blob = await response.blob();

    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "stego.png";
    document.body.appendChild(a);
    a.click();
    a.remove();
});
</script>